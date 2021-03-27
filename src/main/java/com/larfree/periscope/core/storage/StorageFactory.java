package com.larfree.periscope.core.storage;

/**
 * @author Blues
 * @date 2021/3/22 9:59 下午
 **/
public class StorageFactory {

    static public Storage getInstance(String type) {
        Storage storage;
        switch (type) {
            case "redis":
            default:
                storage = new RedisStorage();
                break;
        }
        return storage;
    }

    static public Storage getInstance() {
        return getInstance("redis");
    }
}
