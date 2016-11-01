# company  人力资源公司的一些基础信息，并且是保存在mongo数据库的。


此为公司审核的 package

   CheckCompanySignupInfoDto   是新地点的工作人员审核新注册公司的时候，所需要的dto
   
   CompanySignupItems   公司注册时候，具体需要的条目，这些条目是有限的，所以现在定义为enum
                         *
                         * 还有一个目的，就是方便新地点的工作人员审核公司的时候，一一比对这些已有的条目，然后为这些
                         * 条目增加拒绝的理由（如果是拒绝申请了这家公司）
                         
                         
    Reject          当审核公司，注册材料的时候，如果拒绝了他的提交申请，那么必需提交，拒绝的理由
                    *
                    * 拒绝的理由，由两部分组成，key（是提交申请的条目），message，该条目拒绝的理由