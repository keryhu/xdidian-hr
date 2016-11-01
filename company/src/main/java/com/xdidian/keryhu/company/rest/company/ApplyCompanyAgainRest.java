package com.xdidian.keryhu.company.rest.company;

import com.xdidian.keryhu.company.domain.company.create.NewCompanyWaitCheckedDto;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.ConvertUtil;
import com.xdidian.keryhu.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by hushuming on 2016/10/10.
 * <p>
 * 当用户提交的公司注册资料，被拒绝后，再次编辑，提交申请的rest
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ApplyCompanyAgainRest {

    private final CompanyRepository repository;
    private final ConvertUtil convertUtil;


    /**
     * 首先用户，需要获取已经提交过的公司信息，并且获取被拒绝的  哪一项或哪几项有问题，的 数据,
     * 查找的是 本人id的。也就是当前 在线用户。
     * <p>
     * 必需要将用户的姓名传回来，因为注册时候，提供了姓名，再次编辑时候也需要。通过spring feign
     */
    @GetMapping("/company/getRejectCompanyInfo")
    public ResponseEntity<?> getRejectCompanyInfo() {
        String id = SecurityUtils.getCurrentLogin();

        NewCompanyWaitCheckedDto dto = repository.findByAdminId(id)
                .stream()
                .filter(e -> !e.isChecked())
                .filter(e -> e.getRejects() != null)
                .map(e -> convertUtil.companyToNewCompanyWaitCheckedDto.apply(e))
                .findFirst()
                .orElse(null);

        return ResponseEntity.ok(dto);
    }
}
