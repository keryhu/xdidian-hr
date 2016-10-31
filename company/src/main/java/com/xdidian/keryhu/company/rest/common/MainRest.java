package com.xdidian.keryhu.company.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xdidian.keryhu.company.domain.Department;
import com.xdidian.keryhu.company.domain.Office;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.ConvertUtil;
import com.xdidian.keryhu.tree.LinkedMultiTreeNode;
import com.xdidian.keryhu.tree.TreeNode;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainRest {


  private final ConvertUtil convertUtil;
  private final CompanyRepository repository;

  @GetMapping("/query/2")
  public Department d() {

    TreeNode<Office> o1 = new LinkedMultiTreeNode<Office>(new Office("2222"));
    TreeNode<Office> o2 = new LinkedMultiTreeNode<Office>(new Office("3333"));
    TreeNode<Office> o3 = new LinkedMultiTreeNode<Office>(new Office("4444"));
    TreeNode<Office> o4 = new LinkedMultiTreeNode<Office>(new Office("5555"));
    o1.add(o2);
    o1.add(o3);
    o2.add(o4);
    Department d = new Department("nn");
    d.setOffices(o1);
    return d;

  }



  
  
}
