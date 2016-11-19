# xdidian-hr

这个方案是使用的 spring oauth2 stateless 和 angular2 ，携带 access——token的方案。


一 、 xdidian  micro server docker 
这是自行开发企业项目的的github代码库
spring micro－service cloud docker domo

二 、 目前启动顺序是： eureka －> config server(10s)8888 ->user(20s)-> auth server(30s)9999
-> mail(40s) 8002-> signup 8003 (50s)->account-activation(60s)->
company(70s)->menu 边栏菜单(80s) 8006->websocket(90)8007->message(100) 8008
 ->pc gateway(120s)
 等其他的

三 、 目前是每一个service单独的数据库，相互之间没有实现message broker。

四 、 增加了在指定限制时间内email激活账户的功能，如果未激活不能登录账户，超时了未激活，自动删除账户。


四 、 更新于 2016-10-30: 接下来需要实现的功能： 
     
     接下来的工作1
     1 公司审核
     2 加入公司
     
     
     
  
 

 
   