package com.xdidian.keryhu.menu.client;

import com.xdidian.keryhu.menu.domain.feign.LoggedWithMenuDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class UserClientFallback implements UserClient{


  @Override
  public LoggedWithMenuDto getIsInCompanyAndName(@RequestParam("id") String id,
                                                 @RequestHeader("Authorization") String token) {
      return new LoggedWithMenuDto();
  }
}
