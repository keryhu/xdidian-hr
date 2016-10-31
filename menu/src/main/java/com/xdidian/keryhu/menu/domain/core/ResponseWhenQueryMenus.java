package com.xdidian.keryhu.menu.domain.core;

import com.xdidian.keryhu.menu.domain.core.MenuDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hushuming on 2016/10/15.
 * //因为登录了系统，这里附带上，需要的个人信息，现在加上用户的姓名
 */
@Getter
@Setter
public class ResponseWhenQueryMenus implements Serializable {

    public String name; // 用户姓名
    public List<MenuDto> menus;    //用户当前的菜单。
}
