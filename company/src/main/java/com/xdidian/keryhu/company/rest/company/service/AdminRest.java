package com.xdidian.keryhu.company.rest.company.service;

import com.xdidian.keryhu.company.domain.company.Company;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by hushuming on 2016/10/29.
 * <p>
 * 新地点的管理人员，对于company 组件的操作。
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class AdminRest {


    private final CompanyRepository repository;

    /**
     * 新地点的客服人员，根据公司的名字 搜索公司信息
     */
    @GetMapping("/admin/queryCompanyWithPage")
    public Page<Company> get(

            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("name") String name,
            @RequestParam(value = "registerTimeBegin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registerTimeBegin,
            @RequestParam(value = "registerTimeEnd", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registerTimeEnd) {

        return repository.findByName(name, pageable);
    }
}
