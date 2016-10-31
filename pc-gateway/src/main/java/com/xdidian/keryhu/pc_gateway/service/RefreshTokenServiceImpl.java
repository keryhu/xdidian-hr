package com.xdidian.keryhu.pc_gateway.service;

import static com.xdidian.keryhu.util.StringValidate.isUuid;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xdidian.keryhu.pc_gateway.domain.RefreshToken;
import com.xdidian.keryhu.pc_gateway.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("refreshTokenService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshTokenServiceImpl implements RefreshTokenService {
  
  private final RefreshTokenRepository repository;
  private final int reFreshTokenValiditySeconds = 864000;


  @Override
  public void validate(RefreshToken token) {
    // TODO Auto-generated method stub
    Assert.isTrue(isUuid(token.getUserId()),
        "userId格式不正确！");
    
    Assert.hasText(token.getRefreshToken(), "RefreshToken不能为空");
    

  }

  @Override
  public Map<String, String> getRefreshToken(String userId) {
    // TODO Auto-generated method stub
    Map<String, String> map = new HashMap<String, String>();
    String token = repository.findByUserId(userId).map(e -> e.getRefreshToken()).orElse("");
    map.put("refreshToken", token);
    return map;
  }

  @Override
  public void storeRefreshToken(RefreshToken token) {
    // TODO Auto-generated method stub
    this.validate(token);
    boolean m=repository.findByUserId(token.getUserId()).isPresent();
    repository.findByUserId(token.getUserId()).ifPresent(e->{
      e.setExpiredTime(LocalDateTime.now().plusSeconds(reFreshTokenValiditySeconds));
      e.setRefreshToken(token.getRefreshToken());
      log.info("store refreshToken exist store-- {} ",token);
      repository.save(e);
    });
    
    if(!m){
      token.setExpiredTime(LocalDateTime.now().plusSeconds(reFreshTokenValiditySeconds));
      log.info("store refreshToken new store-- {} ",token);
      repository.save(token);
    }
  }


}
