package com.xdidian.keryhu.company.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by hushuming on 2016/10/8.
 * 当有新的公司注册的时候，发送新的message出去，此为发送的具体方法，
 * 接收方为 websocket，接受到消息后，发送消息给angular2 前台 和app。
 */

@Component
@EnableBinding(NewCompanyOutputChannel.class)
@Slf4j
public class NewCompanyProducer {


    @Autowired
    private NewCompanyOutputChannel channel;


    public void send(String content) {

        boolean result = channel.newCompany()
                .send(MessageBuilder.withPayload(content).build());

        Assert.isTrue(result, "新公司注册，服务器从company_info发送message消息失败！");

        log.info("服务器发送 信息公司注册成功的消息成功！");

    }

}
