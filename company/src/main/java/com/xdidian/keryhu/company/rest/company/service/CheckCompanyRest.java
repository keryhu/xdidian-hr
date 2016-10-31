package com.xdidian.keryhu.company.rest.company.service;

import com.xdidian.keryhu.company.client.UserClient;
import com.xdidian.keryhu.company.domain.feign.EmailAndPhoneDto;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.CompanyService;
import com.xdidian.keryhu.company.stream.CheckNewCompanyProducer;
import com.xdidian.keryhu.domain.CheckType;
import com.xdidian.keryhu.domain.company.CheckCompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hushuming on 2016/10/9.
 *
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
    @GetMapping("/service/check-company-resolve")
    public ResponseEntity<?> getAllUncheckedCompany() {

        return ResponseEntity.ok(companyService.findUncheckedCompany());
    }


    /**
     * 具体审核公司的post，此路由是 同意公司的注册资料，表明审核通过，发送message，接收方有邮件服务器，手机平台，websocket
     * <p>
     * 通过传递上来的，checkType，判断是同意，还是拒绝，该companyId的数据。
     * <p>
     * <p>
     * 当新公司注册后，新地点的工作人员，
     * 审核通过了公司的注册资料，由company_info，发出的，消息，
     * 1 给user——account，通知他更新 user 的权限为 ROLE_COMPANY_ADMIN，更新companyId为新的。（id或email或phone，和companyId）
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
     * ----审核的时候，提交的项目中，具体哪一项，或者哪几项不符合要求，具体钩出来。前台作出 勾选并且提交给后台。
     * <p>
     * 提交的格式是：可选项： xx项目，拒绝原因：xxx；这是map<String,String>。
     */

    @PostMapping("/service/check-company")
    public ResponseEntity<?> agreeCompany(
            @RequestParam("companyId") String[] companyId,
            @RequestParam("checkType") String checkType,
            @RequestParam(value = "reject", required = false)
                    Map<String, String> reject,
            @RequestHeader("Authorization") String token) {

        boolean ct = checkType.equals(CheckType.AGREE.toValue()) ||
                checkType.equals(CheckType.REJECT.toValue());
        Assert.isTrue(ct, "上传的checkType不正确！");

        // 正面提交过来的reject key 信息是正确的。
        boolean k = reject.keySet().stream()
                .allMatch(e -> companyService.isKeyExistInRejectCompany(e));

        Assert.isTrue(k, "reject信息不正确！");

        if (reject == null) {
            Assert.isTrue(checkType.equals(CheckType.AGREE.toValue()), "上传的参数不对！");
        } else {
            Assert.isTrue(checkType.equals(CheckType.REJECT.toValue()), "上传的参数不对！");
        }

        Map<String, Boolean> map = new HashMap<>();

        Assert.notEmpty(companyId, "adminId数组不能为空");
        //根据companId，查询本地的adminId，然后根据adminId，通过feign查询对应的email和phone 对象。
        CheckCompanyDto[] a = Arrays.stream(companyId)
                .map(e -> repository.findById(e).orElse(null))
                .filter(e -> e != null)
                .map(e -> {
                    CheckCompanyDto d = new CheckCompanyDto();
                    String userId = e.getAdminId();
                    EmailAndPhoneDto ep = userClient.getEmailAndPhoneById(userId, token);
                    d.setEmail(ep.getEmail());
                    d.setPhone(ep.getPhone());
                    d.setCompanyId(e.getId());
                    d.setUserId(userId);
                    d.setCheckType(CheckType.forValue(checkType));
                    return d;
                })
                .toArray(CheckCompanyDto[]::new);

        checkNewCompanyProducer.send(a);

        //保存本地
        Arrays.stream(a)
                .map(e -> repository.findById(e.getCompanyId()).get())
                .forEach(e -> {
                    if (checkType.equals(CheckType.AGREE.toValue())) {
                        e.setChecked(true);

                    } else {
                        e.setChecked(false);
                        e.setReject(reject);
                    }
                    e.setCheckedTime(LocalDateTime.now());
                    repository.save(e);
                });

        //审核完成，发送结果给前台。
        map.put("result", true);
        return ResponseEntity.ok(map);
    }


}
