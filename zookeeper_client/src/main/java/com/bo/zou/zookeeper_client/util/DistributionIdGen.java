package com.bo.zou.zookeeper_client.util;

import com.bo.zou.zookeeper_client.configuration.ZKClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;

public class DistributionIdGen {
    private static final String LOCK_PATH = "/id";

    private ZKClient zkClient;

    private InterProcessMutex mutex;

    public DistributionIdGen(ZKClient zkClient) {
        this.zkClient = zkClient;
        this.mutex = new InterProcessMutex(zkClient.getClient(), LOCK_PATH);
    }

    public int nextId() throws Exception {
        int seq = 0;
        try {
            mutex.acquire();
            String id = zkClient.getData(LOCK_PATH);
            if (!"".equals(id)) {
                seq = Integer.parseInt(zkClient.getData(LOCK_PATH));
            }
            seq++;
            zkClient.setData(LOCK_PATH, String.valueOf(seq));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }

        return seq;
    }

}
