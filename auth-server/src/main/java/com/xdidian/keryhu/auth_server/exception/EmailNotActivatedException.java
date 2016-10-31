package com.xdidian.keryhu.auth_server.exception;

/**
 * 
 * @Description : 当客户端登录时，ip被冻结出现的错误
 * @date : 2016年6月18日 下午7:56:41
 * @author : keryHu keryhu@hotmail.com
 */
public class EmailNotActivatedException extends RuntimeException {

  private static final long serialVersionUID = 6402749550958017985L;

  public EmailNotActivatedException(String message) {
    super(message);
  }

}
