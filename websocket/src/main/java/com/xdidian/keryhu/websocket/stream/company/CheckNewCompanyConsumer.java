package com.xdidian.keryhu.websocket.stream.company;


import com.xdidian.keryhu.domain.company.CheckCompanyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;


/**
 * Created by hushuming on 2016/10/10.
 * <p>
 * 当新公司注册完成后，新地点的工作人员审核完材料后，将审核完的结果从company——info，发送出来，
 * 接受方包含： mail服务器，手机服务器，websocket，user-account，4个。
 * <p>
 * websocket服务器接受到消息后，根据checkType，判断是agree还是reject，
 * 如果是agree，发送company 审核通过的消息出去。
 * 如果是reject，发送company 审核失败的消息出去。
 */

@EnableBinding(CheckNewCompanyInputChannel.class)
@Slf4j
public class CheckNewCompanyConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @StreamListener(CheckNewCompanyInputChannel.NAME)
    public void receive(CheckCompanyDto dto) {

        log.info("websocket 接受到来自company_info新公司注册审核结果的消息，现在准备发送websocket出去！");

        //客户端的接受 主题是：stompClient.subscribe('/userId/' + '/queue/checkNewCompany,...)

        if (dto != null) {
            simpMessagingTemplate.convertAndSendToUser(dto.getUserId(),
                    "/queue/checkNewCompany",dto.getCheckType());
        }
    }


}
