package com.xdidian.keryhu.company.rest.company.service;

import com.xdidian.keryhu.company.client.UserClient;
import com.xdidian.keryhu.company.domain.company.Company;
import com.xdidian.keryhu.company.domain.company.QCompany;
import com.xdidian.keryhu.company.domain.company.check.CheckCompanySignupInfoDto;
import com.xdidian.keryhu.company.domain.company.check.Reject;
import com.xdidian.keryhu.company.domain.feign.EmailAndPhoneDto;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.CompanyService;
import com.xdidian.keryhu.company.stream.CheckNewCompanyProducer;
import com.xdidian.keryhu.domain.CheckType;
import com.xdidian.keryhu.domain.company.CheckCompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;


/**
 * Created by hushuming on 2016/10/9.
 * <p>
 * 新地点的工作人员，审核公司注册资料的 rest
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckCompanyRest {

    private final CompanyService companyService;
    private final CompanyRepository repository;
    private final UserClient userClient;
    private final CheckNewCompanyProducer checkNewCompanyProducer;

    //

    /**
     * 此路由，需要新地点客服或者新地点管理员权限,此路由仅仅获取当前未审核公司的所有数据
     * <p>
     * 当新地点的客服人员，或者管理人员,进入／service/check-company，审核新的注册公司的时候，促发的rest，
     * 审核后的结果，要不审核通过，要不拒绝，填写拒绝理由给 前台。
     */
    // 搜索所有未审核的公司   新地点的客服人员和工作人员，都使用这个url和方法。
    @GetMapping("/service/queryUncheckedCompanyWithPage")
    public Page<Company> getUncheckedCompany(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "content", required = false) String content) {


        QCompany company = new QCompany("company");
        if(content==null){
            return repository.findAll(pageable);
        }
        else {
            com.querydsl.core.types.Predicate predicate = company.name.like(content)
                    .and(company.checked.eq(false));

            return repository.findAll(predicate, pageable);
        }

    }


    /**
     * 具体审核公司的post，此路由是 同意公司的注册资料，表明审核通过，发送message，接收方有邮件服务器，手机平台，websocket
     * <p>
     * 通过传递上来的，checkType，判断是同意，还是拒绝，该companyId的数据。
     * <p>
     * <p>
     * 当新公司注册后，新地点的工作人员，
     * 审核通过了公司的注册资料，由company，发出的，消息，
     * 1 给user，通知他更新 user 的权限为 ROLE_COMPANY_ADMIN，更新companyId为新的。（id或email或phone，和companyId）
     * 2 通知邮件服务器，发送审核成功的通知，(email-必需，companyId）。
     * 3 通知手机平台，发送审核成功的通知，（phone--必需，companyId）
     * 4 通知websocket，给对应的userId，发送通知（userId-必需，companyId）
     * <p>
     * 5 如果审核通过，需要更改本地的 company数据库，checked为true，更新checkedTime为当前时间
     * user-account服务器，设置该user的权限为 公司的管理员，更改companyId，。注意提交的是 companyId，数组。
     * <p>
     * <p>
     * 具体审核公司的post，此路由是 拒绝公司的注册资料，并将拒绝的理由保存起来。接收方有邮件服务器，手机平台，websocket。
     * 注意提交的是 companyId，数组。
     * 审核失败了：
     * 1 通知邮件服务器，发送审核失败的通知，(email-必需，companyId）。
     * 3 通知手机平台，发送审核失败的通知，（phone--必需，companyId）
     * 4 通知websocket，给对应的userId，发送失败通知（userId-必需，companyId）
     * <p>
     * 5 如果审核通过，需要更改本地的 company数据库，checked为false，更新checkedTime为当前时间，加上reject保存
     * <p>
     * <p>
     * <p>
     * 提交的格式是：可选项： xx项目，拒绝原因：xxx；这是map<String,String>。
     */

    @PostMapping("/service/check-company")
    public ResponseEntity<?> agreeCompany(
            @RequestBody CheckCompanySignupInfoDto dto,
            @RequestHeader("Authorization") String token) {


        Assert.notNull(dto.getCompanyId(), "companyId 必填！");
        Assert.isTrue(repository.findById(dto.getCompanyId()).isPresent(), "companyId 无效！");
        Assert.notNull(dto.getCheckType(), "checkType不能为空");
        // 如果是拒绝了，那么必需提供拒绝的理由
        if (dto.getCheckType().equals(CheckType.REJECT)) {

            Assert.notNull(dto.getRejects(), "必需提供拒绝的理由！");
            // 每一个拒绝的理由中，必需提供
            Predicate<Reject> rejectAllNotNull = x -> x.getItem() != null && x.getMessage() != null;
            boolean m = dto.getRejects().stream().allMatch(rejectAllNotNull);
            Assert.isTrue(m, "必需填写拒绝的条目和理由");
        }


        Map<String, Boolean> map = new HashMap<>();

        // 为什么需要 repostiroy.findById。因为需要通过companyId，查找到他的adminId，
        // 再找到注册申请人的email，phone，这样同意或拒绝申请材料，才可以通知到他

        // 将本地的company对象，转为CheckCompanyDto，方便发送message出去。
        CheckCompanyDto checkCompanyDto = repository.findById(dto.getCompanyId())
                .map(e -> {
                    CheckCompanyDto d = new CheckCompanyDto();
                    String userId = e.getAdminId();
                    EmailAndPhoneDto ep = userClient.getEmailAndPhoneById(userId, token);
                    d.setEmail(ep.getEmail());
                    d.setPhone(ep.getPhone());
                    d.setCompanyId(e.getId());
                    d.setUserId(userId);
                    d.setCheckType(dto.getCheckType());
                    // 如果是同意了申请，那么更新数据库资料
                    if (dto.getCheckType().equals(CheckType.AGREE)) {
                        e.setChecked(true);
                    } else {
                        e.setChecked(false);
                        e.setRejects(dto.getRejects());
                    }
                    e.setCheckedTime(LocalDateTime.now());
                    repository.save(e);
                    return d;
                }).orElse(null);


        checkNewCompanyProducer.send(checkCompanyDto);


        //审核完成，发送结果给前台。
        map.put("result", true);
        return ResponseEntity.ok(map);
    }


}
