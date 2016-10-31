package com.xdidian.keryhu.menu.client;

import com.xdidian.keryhu.menu.domain.feign.LoggedWithMenuDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "account", fallback = UserClientFallback.class)
public interface UserClient {

    // 这里获取 ，用户是否 已经注册公司，和用户的姓名，一起返回给前台。,id is userId

    @RequestMapping(value = "/users/getIsInCompanyAndName", method = RequestMethod.GET)
    public LoggedWithMenuDto getIsInCompanyAndName(
            @RequestParam("id") String id,
            @RequestHeader("Authorization") String token);

}
