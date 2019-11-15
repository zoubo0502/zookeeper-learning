package com.bo.zou.zookeeper_config.configuration;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class ZKClient {
    private final Logger logger = LoggerFactory.getLogger(ZKClient.class);


    private static final int SLEEP_TIME_MS = 1000;
    private static final int MAX_RETRIES = 3;

    private String zookeeperServer;

    public ZKClient(String zookeeperServer) {
        this.zookeeperServer = zookeeperServer;
    }

    private CuratorFramework client;

    public void init() {
        this.client = CuratorFrameworkFactory.builder().connectString(this.getZookeeperServer()).retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME_MS, MAX_RETRIES)).build();
        this.client.start();
    }

    public void createNode(String path, String data) throws Exception {

            client.create().creatingParentsIfNeeded() // 若创建节点的父节点不存在则先创建父节点再创建子节点
                    .withMode(CreateMode.PERSISTENT) // 创建的是持久节点
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // 默认匿名权限,权限scheme id:'world,'anyone,:cdrwa
                    .forPath(path, data.getBytes());

    }

    public String getData(String path) throws Exception {
        return new String(client.getData().forPath(path));
    }

    public void setData(String path, String data) throws Exception {
        if (client.checkExists().forPath(path) == null) {
            createNode(path, data);
        } else {
            client.setData().forPath(path, data.getBytes());
        }
    }

    public void destroy() {
        try {
            if (getClient() != null) {
                getClient().close();
            }
        } catch (Exception e) {
            logger.error("stop zookeeper client error {}", e.getMessage());
        }
    }
}
