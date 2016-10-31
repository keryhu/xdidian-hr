package com.xdidian.keryhu.company.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by hushuming on 2016/10/8.
 * 当有新的公司注册的时候，发送新的message出去，此为chaneel，
 * 接收方为 websocket，接受到消息后，发送消息给angular2 前台 和app。
 */
public interface NewCompanyOutputChannel {

    // 此channel的值和 application bindings下面的值一致
    String NAME = "newCompanyOutputChannel";

    @Output(NAME)
    MessageChannel newCompany();
}
