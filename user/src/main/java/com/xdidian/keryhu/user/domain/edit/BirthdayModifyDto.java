package com.xdidian.keryhu.user.domain.edit;

import java.io.Serializable;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
* @ClassName: NameModifyDto
* @Description: 当生日修改的时候的dto
* @author keryhu  keryhu@hotmail.com
* @date 2016年9月5日 下午7:45:23
 */


@Data
@NoArgsConstructor
public class BirthdayModifyDto implements Serializable{

  private static final long serialVersionUID = -2280886868429275060L;

  private String userId; // userId

  private int month;     //传递上来 月份
  
  private int date;    //传递上来的  月份的具体几号
  
  private String password;  //此密码是用户输入的 密码，并不是加密密码。
  
  

}
