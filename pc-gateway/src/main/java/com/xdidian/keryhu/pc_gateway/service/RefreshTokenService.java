package com.xdidian.keryhu.pc_gateway.service;

import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import com.xdidian.keryhu.pc_gateway.domain.RefreshToken;

public interface RefreshTokenService {
  
  /**
   * 验证提交的token的合法性
   * @param token
   */
  public void validate(RefreshToken token);
  
  /**
   * 通过userId，来获取，refreshToken
   * 
   */
  
  @PreAuthorize("#n==authentication.name")
  public Map<String, String> getRefreshToken(@Param("n") final String userId);
  
  /**
   * 
  * @Title: storeRefreshToken
  * @Description: TODO(存储 refreshToken)
  * @param @param token    设定文件
  * @return void    返回类型
  * @throws
   */
  
  @PreAuthorize("#n.userId==authentication.name")
  public void storeRefreshToken(@Param("n") final RefreshToken token);

}
