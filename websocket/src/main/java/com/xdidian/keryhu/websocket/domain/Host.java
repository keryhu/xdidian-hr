package com.xdidian.keryhu.websocket.domain;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @Description :获取当前 的 host，
 * @date : 2016年6月18日 下午9:10:25
 * @author : keryHu keryhu@hotmail.com
 */


@Component("host")
@ConfigurationProperties(prefix = "hostProperty")
@Getter
@Setter
public class Host implements Serializable {

    private String hostName;
}