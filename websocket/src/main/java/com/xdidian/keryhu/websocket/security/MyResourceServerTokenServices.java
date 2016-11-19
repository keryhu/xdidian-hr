package com.xdidian.keryhu.websocket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by hushuming on 2016/11/15.
 */

@Component("myResourceServerTokenServices")
public class MyResourceServerTokenServices implements ResourceServerTokenServices {

    private final CustomJwtTokenConverter customJwtTokenConverter;

    @Autowired
    public MyResourceServerTokenServices(CustomJwtTokenConverter customJwtTokenConverter) {
        this.customJwtTokenConverter = customJwtTokenConverter;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {
        Assert.hasLength(accessToken,"token 不能为空！");
        OAuth2AccessToken token = customJwtTokenConverter.
                extractAccessToken(accessToken);
        return customJwtTokenConverter.
                extractAuthentication(token.getAdditionalInformation());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return customJwtTokenConverter.extractAccessToken(accessToken);
    }
}
