# pc-gateway
一 、 最后一个启动的服务。主要实现的功能－－ pc html5 gateway 实现。

二 、 使用spring proxy zuul 将后台所有的接口，自动路由到各个micro－service ，因为有了spring proxy zuul，前台调用其它服务器service的方法，都必须加上对应的 路由前置。

  
三 、 实现了spring OAuth2 SSO 登陆。

四 、 开启了csrf 验证，启用了 logout 前台注销 ，（后台并没有真正注销，这是jwt的一个缺点，但是令牌会刷新，所以后台注销暂时不考虑），另外前台注销后，js 进行导航到新的页面。

五 、 增加了role权限排序功能，即admin用户可以打开需要property权限验证的页面

六 、 前台页面全部移到了angular2.