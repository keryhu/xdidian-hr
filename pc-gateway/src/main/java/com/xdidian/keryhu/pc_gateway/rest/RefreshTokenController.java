package com.xdidian.keryhu.pc_gateway.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xdidian.keryhu.pc_gateway.domain.RefreshToken;
import com.xdidian.keryhu.pc_gateway.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j

public class RefreshTokenController {

  private final RefreshTokenService tokenService;

  @GetMapping("/api/getRefreshToken")
  //已经加了安全 验证
  public ResponseEntity<Map<String, String>> getRefreshToken(
      @RequestParam("userId") final String userId) {
    log.info("从服务器下载新的refreshToken");

    return ResponseEntity.ok().body(tokenService.getRefreshToken(userId));
  }

  /**
   * 当loginName已经存在的情况下，局部更新，否则新建,已经加了安全 验证
   *
   */
  

  @PostMapping("/api/storeRefreshToken")
  public void storeRefreshToken(@RequestBody final RefreshToken token) {
    log.info("store refreshToken 0-- {} ", token);
    tokenService.storeRefreshToken(token);

  }

}
