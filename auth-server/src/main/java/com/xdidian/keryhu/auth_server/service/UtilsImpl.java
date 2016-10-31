package com.xdidian.keryhu.auth_server.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component("utils")
public class UtilsImpl implements Utils {

  /**
   * 获取用户本地当前ip地址
   */
  @Override
  public String getIp(HttpServletRequest request) {
    String xfHeader = request.getHeader("X-Forwarded-For");
    return (xfHeader == null || xfHeader.isEmpty()) ? request.getRemoteAddr()
        : xfHeader.split(",")[0];
  }

}
