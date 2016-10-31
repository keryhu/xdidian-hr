package com.xdidian.keryhu.company.rest.common;

import com.xdidian.keryhu.company.stream.NewCompanyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.xdidian.keryhu.util.Constants.NEW_COMPANY;

/**
 * Created by hushuming on 2016/10/9.
 */

@RestController
public class TestRest {

    @Autowired
    private NewCompanyProducer producer;

    @GetMapping("/query/test")
    public ResponseEntity<?> test() {

        Map<String, Boolean> map = new HashMap<>();

        map.put("result", true);
        producer.send(NEW_COMPANY);
        return ResponseEntity.ok(map);
    }
}
