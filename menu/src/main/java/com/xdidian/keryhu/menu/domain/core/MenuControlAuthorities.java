package com.xdidian.keryhu.menu.domain.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
* @ClassName: MenuControlAuthorities
* 针对菜单的控制权限，包含了读写改删)
* @author keryhu  keryhu@hotmail.com
* @date 2016年8月10日 下午9:39:17
 */
public enum MenuControlAuthorities {
  READ,
  EDIT,
  ADD,
  REMOVE;
  
  private static Map<String, MenuControlAuthorities> menuControlAuthoritiesMap = new HashMap<String, MenuControlAuthorities>(4);
  
  static{
    menuControlAuthoritiesMap.put("READ",READ );
    menuControlAuthoritiesMap.put("EDIT",EDIT );
    menuControlAuthoritiesMap.put("ADD",ADD );
    menuControlAuthoritiesMap.put("REMOVE",REMOVE );
  }
  
  @JsonCreator
  public static MenuControlAuthorities forValue(String value) {
    return menuControlAuthoritiesMap.get(value);
  }

  @JsonValue
  public String toValue() {
    for (Entry<String, MenuControlAuthorities> menu : menuControlAuthoritiesMap.entrySet()) {
      if (menu.getValue() == this)
        return menu.getKey();
    }
    return null;
  }
  
  
  public String getName(){
    MenuControlAuthorities a=this;
    String result="";
    switch(a){
      case READ:
       result="读";
       break;
      case EDIT:
        result="编辑";
        break;
      case ADD:
        result="新建";
        break;
      case REMOVE:
        result="删除";
        break;
      default:
        break;
      
    }
    return result;
  }

}
