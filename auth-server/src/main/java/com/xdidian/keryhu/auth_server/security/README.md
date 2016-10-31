#OAuth2 -server

security 的配置package

CustomAuthenticationProvider

     实现了AuthenticationProvider接口，用来判断用户的用户名，密码是否输入的正确，
     在这里还实现了： 如果输入错误，进行ipBlock 的记录，+1，如果正确，则清零。
     
     同时还判断，用户的email有没有激活的处理。如果没有激活则报错。
     
     如果密码错误也报错给前台。
     
CustomTokenEnhancer

    自定义jwt 返回给前台 价值额外的信息，例如此处设置了，加载额外的userId
    
