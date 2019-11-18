package com.bo.zou.zookeeper_client.controller;

import com.bo.zou.zookeeper_client.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class Consumer {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping
    public String getProvider() throws Exception {
        return consumerService.getProvider();
    }
}
