# company  人力资源公司的一些基础信息，并且是保存在mongo数据库的。


service package  表示只有新地点的客服或管理员才可以操作的rest

      AdminRest
      
           新地点的管理人才可以操作的 rest
           
           "/admin/queryCompanyWithPage"   根据条件，查询公司的信息，返回page
           

      CheckCompanyRest   需要权限，新地点的客服或管理人
         
           "/service/check-company-resolve"
           
           新地点的工作人员，审核公司注册资料的 rest
           
      serviceRest   新地点的管理人或客服都可以操作
      
           "/service/queryCompanyWithPage"
           根据公司的名字 搜索公司信息 返回page
           
           "/service/queryUncheckedCompanyWithPage"
           搜索所有未审核的公司   新地点的客服人员和工作人员，都使用这个url和方法。

     
ApplyCompanyAgainRest

    "/company/getRejectCompanyInfo"  
    
     当用户提交的公司注册资料，被拒绝后，再次编辑，提交申请的rest
     

     
CompanyRest

    "/company/createCompany"
    公司创建的rest
    
    "/company/isCompanyExist"
    
    查看公司是否存在（参数公司名字）
    
    "/company/createCompanyResolveInfo"
    当打开  新建 公司帐户 页面的时候，，首先需要 加载 3个信息数据
         * 1  所有的 省市，直辖市的 数据
         * 2  所有的公司行业数据
         * 3  所有的公司性质数据
         
    "/company/findUncheckedCompanyBySelf"
    
    用在 用户刚刚登录  后，点击"新建公司"，查看 刚用户是否注册过  公司帐户，
    是否存在需要 审核的 公司帐户。如果存在未审核
        // 的公司帐户，取出companyId，然后前台再通过，companyId，
        查看已经提交的公司信息，查看新地点工作人员有没有
        // 审核该公司，有没有拒绝的理由。  返回companyId 和 checked，
        如果companyId存在，且checked为false，那么就进行上面的判断
        
        
    