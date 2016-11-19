package com.xdidian.keryhu.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
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
@EnableScheduling
public class WebSocketBrokerConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {


    /**
     * public void registerStompEndpoints(StompEndpointRegistry registry) {
     * <p>
     * <p>
     * //angular2 设置了 proxy ws 代理，所以前台直接访问,不能通过zuul，要不然csrf出错
     * registry.addEndpoint("/websocket/front")
     * .withSockJS()
     * //.setWebSocketEnabled(false)
     * //.setSessionCookieNeeded(false)
     * .setStreamBytesLimit(512 * 1024)
     * .setHttpMessageCacheSize(1000)
     * .setDisconnectDelay(30 * 1000)
     * ;
     * }
     */


    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket/front")
                .addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOrigins("*")
                .withSockJS()
                .setStreamBytesLimit(512 * 1024)
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(30 * 1000);

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
        //config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/pcAngular2");
    }


    /**
     *
     * @Override public void configureClientInboundChannel(ChannelRegistration registration) {

    registration.setInterceptors(new ChannelInterceptorAdapter() {
    @Override public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    List tokenList = accessor.getNativeHeader("X-Auth-Token");
    accessor.removeNativeHeader("X-Auth-Token");
    String token = null;
    if (tokenList != null && tokenList.size() > 0) {
    token = (String) tokenList.get(0);
    }
    Principal auth = token == null ? null :
    myResourceServerTokenServices.loadAuthentication(token);
    if(auth == null) {
    auth = new AnonymousAuthenticationToken("websocket",
    "anonymous",Collections.
    singleton(new SimpleGrantedAuthority("")));
    }
    if(accessor.getMessageType()!=null){
    switch ((accessor.getMessageType())){
    case CONNECT:
    userRegistry.onApplicationEvent(new SessionConnectedEvent(this, (Message<byte[]>) message, auth));
    break;
    case SUBSCRIBE:
    userRegistry.onApplicationEvent(new SessionSubscribeEvent(this, (Message<byte[]>) message, auth));
    break;
    case UNSUBSCRIBE:
    userRegistry.onApplicationEvent(new SessionUnsubscribeEvent(this, (Message<byte[]>) message, auth));
    break;
    case DISCONNECT:
    userRegistry.onApplicationEvent(new SessionDisconnectEvent(this, (Message<byte[]>) message, accessor.getSessionId(), CloseStatus.NORMAL));
    break;
    default:
    break;
    }
    }

    accessor.setUser(auth);

    // not documented anywhere but necessary otherwise NPE in StompSubProtocolHandler!
    accessor.setLeaveMutable(true);
    return MessageBuilder.createMessage(message.getPayload(),
    accessor.getMessageHeaders());
    }

    });
    }
     *
     * @param registration
     */


}
