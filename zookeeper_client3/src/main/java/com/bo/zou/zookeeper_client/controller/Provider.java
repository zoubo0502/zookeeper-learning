package com.bo.zou.zookeeper_client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class Provider {

    @GetMapping
    public String getProvider() {
        return "hi provider 2";
    }
}
