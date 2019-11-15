package com.bo.zou.zookeeper_config.controller;

import com.bo.zou.zookeeper_config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @GetMapping("/{config}")
    public void test(@PathVariable String config) {
        System.out.println(config);
        configService.updateConfig(config);
    }
}
