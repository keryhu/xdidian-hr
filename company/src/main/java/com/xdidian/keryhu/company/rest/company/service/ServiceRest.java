package com.xdidian.keryhu.company.rest.company.service;

import com.querydsl.core.types.Predicate;
import com.xdidian.keryhu.company.domain.company.Company;
import com.xdidian.keryhu.company.domain.company.QCompany;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyWaitCheckedDto;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hushuming on 2016/10/29.
 * <p>
 * 新地点的客服人员，搜索公司  需要的rest。  首页的搜索
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceRest {

    private final CompanyRepository repository;
    private final ConvertUtil convertUtil;

    /**
     * 新地点的客服人员，根据公司的名字 搜索公司信息,查询的时候，实现输入公司名字的关键字即可查询
     */
    @GetMapping("/service/queryCompanyWithPage")
    public Page<Company> get(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "content") String content) {

        return repository.findByNameLike(content, pageable);
    }


    /**
     * 新地点的客服或管理人员，进入审核页面后，搜索所有未审核的注册公司，当点击某一个公司详情
     * 的时候，通过companyId，来获取他的提交材料，通过这个rest完成
     *
     * 这里返回的对象，用的是 带有 reject 参数的，没有问题，因为此时reject还没有值
     */
    @GetMapping("/service/queryNewCompanyInfoByCompanyId")
    public ResponseEntity<?> getRepository(
            @RequestParam(value = "companyId") String companyId) {

        NewCompanyWaitCheckedDto dto =repository.findById(companyId)
                .map(e->convertUtil.companyToNewCompanyWaitCheckedDto.apply(e))
                .orElse(null);
        return ResponseEntity.ok(dto);
    }
}
