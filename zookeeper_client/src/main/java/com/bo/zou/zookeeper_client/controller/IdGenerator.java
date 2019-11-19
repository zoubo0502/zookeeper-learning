package com.bo.zou.zookeeper_client.controller;

import com.bo.zou.zookeeper_client.util.DistributionIdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
public class IdGenerator {
    @Autowired
    private DistributionIdGen distributionIdGen;
    @GetMapping
    public int getId() throws Exception {
        return distributionIdGen.nextId();
    }
}
