package com.xdidian.keryhu.auth_server.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 
 * @Description : auth-server security主方法。注意不要使用 @RequiredArgsConstructor(onConstructor
 *              = @__(@Autowired)))
 * @date : 2016年6月18日 下午8:05:55
 * @author : keryHu keryhu@hotmail.com
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  //也不能，变成 constructor autowired，只能目前这个样子，不要更改
  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;


  /**
   * 自定义的登录访问控制。
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(customAuthenticationProvider);
  }



  /**
   * 注意对于auth-server 里的url 路由security 控制，都加到 ResourceServerConfig class 里，不要加到此class 方法里。
   * </p>
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.httpBasic().realmName("xdidian")
    .and().sessionManagement()
       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest().authenticated();

    // @formatter:on
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }



}
