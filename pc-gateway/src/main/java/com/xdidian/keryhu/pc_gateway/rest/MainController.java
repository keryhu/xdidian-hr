package com.xdidian.keryhu.pc_gateway.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 附属的rest设置
 * @date : 2016年6月18日 下午9:10:56
 * @author : keryHu keryhu@hotmail.com
 */
@RestController
public class MainController {


    @GetMapping("/api/hello")
  public String hello() {
    return "Hello this is pc-gateway'hello page!";
  }

    @GetMapping("/api/test")
  public String test() {
    return "Hello this is pc-gateway'test  page , it not protected !";
  }
  
  
  


}
