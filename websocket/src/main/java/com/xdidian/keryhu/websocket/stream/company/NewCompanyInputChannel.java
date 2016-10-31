package com.xdidian.keryhu.websocket.stream.company;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by hushuming on 2016/10/8.
 * <p>
 * 当有新的公司创建的时候，由company，发出的消息，
 * <p>
 * 此为接受消息的一方，一旦接受到消息，以websocket的形式发送到angular2前台和app
 */


public interface NewCompanyInputChannel {

    String NAME = "newCompanyInputChannel";

    @Input(NAME)
    SubscribableChannel receive();


}
