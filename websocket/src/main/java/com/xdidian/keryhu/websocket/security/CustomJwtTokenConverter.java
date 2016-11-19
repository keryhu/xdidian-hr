package com.xdidian.keryhu.websocket.security;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

/**
 * Created by hushuming on 2016/11/15.
 */
@Component
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    public OAuth2AccessToken extractAccessToken(String value) {
        return super.extractAccessToken(value, decode(value));
    }
}
