package com.xdidian.keryhu.company.rest.company.service;

import com.querydsl.core.types.Predicate;
import com.xdidian.keryhu.company.domain.company.Company;
import com.xdidian.keryhu.company.domain.company.QCompany;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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


    /**
     * 新地点的客服人员，根据公司的名字 搜索公司信息,查询的时候，实现输入公司名字的关键字即可查询
     */
    @GetMapping("/service/queryCompanyWithPage")
    public Page<Company> get(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "content") String content) {

        return repository.findByNameLike(content, pageable);
    }




}
