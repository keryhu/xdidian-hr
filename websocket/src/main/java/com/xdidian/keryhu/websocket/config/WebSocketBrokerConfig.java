package com.xdidian.keryhu.websocket.config;

import com.xdidian.keryhu.websocket.domain.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * Created by hushuming on 2016/10/5.
 * <p>
 * pc-gateway 向pc angular2，网页发送websocket 消息。
 */

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private Host host;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        final String url = new StringBuffer(host.getHostName())
                .append(":8080")
                .toString();


        // client  链接的  socket url   注意 要设置 /pcgateway/front 为free no security
        registry.addEndpoint("/pcgateway/front")
                .setAllowedOrigins(url)
                .withSockJS()
                .setStreamBytesLimit(512 * 1024)
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(30 * 1000)
        ;
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration webSocketTransportRegistration) {

        webSocketTransportRegistration.setSendTimeLimit(15 * 1000)
                .setSendBufferSizeLimit(512 * 1024);
        webSocketTransportRegistration.setMessageSizeLimit(128 * 1024);

    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue/");
        config.setApplicationDestinationPrefixes("/pcAngular2");
    }


}
