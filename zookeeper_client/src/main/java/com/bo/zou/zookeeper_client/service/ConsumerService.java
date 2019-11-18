package com.bo.zou.zookeeper_client.service;

import com.bo.zou.zookeeper_client.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

    @Autowired
    private Client client;

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getProvider() throws Exception {
        String url = client.getUrl();
        if (url == null) {
            return "provider not working now ";
        }
        System.out.println(url);
        return restTemplate().getForObject("http://" + url +"/provider", String.class);
    }
}
