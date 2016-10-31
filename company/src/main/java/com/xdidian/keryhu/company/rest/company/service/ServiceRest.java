package com.xdidian.keryhu.company.rest.company.service;

import com.xdidian.keryhu.company.domain.company.Company;
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
     * 新地点的客服人员，根据公司的名字 搜索公司信息
     */
    @GetMapping("/service/queryCompanyWithPage")
    public Page<Company> get(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("name") String name) {

        return repository.findByName(name, pageable);
    }


    // 搜索所有未审核的公司   新地点的客服人员和工作人员，都使用这个url和方法。
    @GetMapping("/service/queryUncheckedCompanyWithPage")
    public Page<Company> getUncheckedCompany(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("name") String name) {

        //QCompany company = new QCompany("company");
        return repository.findByName(name, pageable);
    }


}
