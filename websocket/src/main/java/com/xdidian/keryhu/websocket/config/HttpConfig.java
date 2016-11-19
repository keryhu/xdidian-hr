package com.xdidian.keryhu.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Created by hushuming on 2016/11/16.
 */
@Configuration
public class HttpConfig {


    //We customize Spring Session’s HttpSession integration to use HTTP headers
    // to convey the current session information instead of cookies.
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}
