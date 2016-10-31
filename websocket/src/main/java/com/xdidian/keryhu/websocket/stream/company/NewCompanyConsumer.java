package com.xdidian.keryhu.websocket.stream.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static com.xdidian.keryhu.util.Constants.NEW_COMPANY;

/**
 * Created by hushuming on 2016/10/8.
 * 当有新的公司创建的时候，由company_inf，发出的消息，
 * <p>
 * 此为接受消息的一方，一旦接受到消息，以websocket的形式发送到angular2前台和app
 */

@EnableBinding(NewCompanyInputChannel.class)
@Slf4j
public class NewCompanyConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @StreamListener(NewCompanyInputChannel.NAME)
    public void receiveNewCompanyMsg(String content) {
        if (content.equals(NEW_COMPANY)) {
            log.info("websocket 接受到来自company_info新公司注册的消息，现在准备发送websocket出去！");

            simpMessagingTemplate.convertAndSend("/topic/newCompany", NEW_COMPANY);
        }
    }

}
