# company  人力资源公司的一些基础信息，并且是保存在mongo数据库的。


checkNewCompany

    审核新公司后发送的message
     当新的公司，审核通过后，发送出去的消息。接收方有4个
     * 1 给user——account，通知他更新 user 的权限为 ROLE_COMPANY_ADMIN，更新companyId为新的。（id或email或phone，和companyId）
     * 2 通知邮件服务器，发送审核成功的通知，(email-必需，companyId）。
     * 3 通知手机平台，发送审核成功的通知，（phone--必需，companyId）
     * 4 通知websocket，给对应的userId，发送通知（userId-必需，companyId）
     具体审核公司的post，此路由是 拒绝公司的注册资料，并将拒绝的理由保存起来。接收方有邮件服务器，手机平台，websocket。
      * 注意提交的是 companyId，数组。
      * 审核失败了：
      * 1 通知邮件服务器，发送审核失败的通知，(email-必需，companyId）。
      * 3 通知手机平台，发送审核失败的通知，（phone--必需，companyId）
      * 4 通知websocket，给对应的userId，发送失败通知（userId-必需，companyId）
      
      
NewCompany

     Created by hushuming on 2016/10/8.
     * 当有新的公司注册的时候，发送新的message出去，此为发送的具体方法，
     * 接收方为 websocket，接受到消息后，发送消息给angular2 前台 和app。