package com.larfree.periscope.storage;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Blues
 * @date 2021/3/15 2:26 下午
 **/
public class RedisStorage implements Storage{
    private String prefixKey = "panda:periscope";
    private StringRedisTemplate stringRedisTemplate;
    private int expireTime = 1000;

    public RedisStorage() {
        stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    }

    @Override
    public Boolean set(String key, String value) {
        value.replaceAll("\\x00\\x00", "");
        stringRedisTemplate.opsForValue().set(prefixKey + ":detial:" + key, value, expireTime, TimeUnit.SECONDS);
        System.out.println("写入成功测试" + prefixKey + ":" + key);
        return true;
    }

    @Override
    public List multiGet() {
        return null;
    }

    /**
     * 保存目录
     * @param rootKey
     * @param indexKey
     * @return
     */
    @Override
    public boolean setList(String rootKey, String indexKey) {
        stringRedisTemplate.opsForList().rightPush(prefixKey+":index:"+rootKey, indexKey);
        stringRedisTemplate.expire(prefixKey+":index:"+rootKey, expireTime, TimeUnit.SECONDS);
        return true;
    }
}
