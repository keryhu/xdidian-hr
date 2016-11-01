package com.xdidian.keryhu.company.service;

import com.xdidian.keryhu.company.domain.Department;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyDto;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyWaitCheckedDto;
import com.xdidian.keryhu.tree.TreeNode;

import java.util.List;

public interface CompanyService {

    //public TreeNode<Department> getDepartment(String companyId);

    // 当新公司注册时候，提交post ，验证信息是否 符合要求。
     void validateNewCompanyPost(final NewCompanyDto dto);

    //查询未审核的公司，如果传递了参数 adminId，那么就查询此adminId下的未审核的公司，如果未提供 adminId，那么就查询所有的
     List<NewCompanyWaitCheckedDto> findUncheckedCompany(final String... adminId);


}
