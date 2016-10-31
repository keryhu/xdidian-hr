package com.xdidian.keryhu.menu.domain.feign;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hushuming on 2016/10/14. feign调用user服务器，在登录的时候，返回给前台。
 * // 这里获取 ，用户是否 已经注册公司，和用户的姓名，一起返回给前台。
 * <p>
 * 一步返回，用户的companyId，和name 信息。
 */

@Getter
@Setter
public class LoggedWithMenuDto implements Serializable {

    private boolean isInCompany;     // 员工是否有companyId。
    private String peopleName;     // 员工姓名
}
