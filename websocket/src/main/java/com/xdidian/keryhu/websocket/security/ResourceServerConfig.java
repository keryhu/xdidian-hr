package com.xdidian.keryhu.websocket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import static com.xdidian.keryhu.util.Constants.READ_AND_WRITE_RESOURCE_ID;


/**
 * @author : keryHu keryhu@hotmail.com
 * @Description : spring OAuth2 Resource 方法
 * @date : 2016年6月18日 下午9:22:20
 */
@Configuration
@EnableResourceServer
@EnableRedisHttpSession
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RoleHierarchy roleHierarchy;

   // @Autowired
   // private MyResourceServerTokenServices myResourceServerTokenServices;

   // @Value("${security.oauth2.resource.jwt.keyValue}")
   // private String publicKey;


    /**
     * 调用spring security role 权限大小排序bean
     */
    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler =
                new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return defaultWebSecurityExpressionHandler;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().and().authorizeRequests().expressionHandler(webExpressionHandler()) // 权限排序
                .mvcMatchers("/favicon.ico","/websocket/front/**").permitAll()
                .anyRequest().authenticated();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        resources.resourceId(READ_AND_WRITE_RESOURCE_ID);
        //resources.tokenServices(myResourceServerTokenServices);
    }


    /**
     *
     * @Bean
     @RefreshScope
     public CustomJwtTokenConverter tokenEnhancer() {
     final CustomJwtTokenConverter customJwtTokenConverter =
     new CustomJwtTokenConverter();
     customJwtTokenConverter.setVerifierKey(publicKey);
     return customJwtTokenConverter;
     }
     *
     * @return
     */







}
