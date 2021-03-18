package com.larfree.periscope.storage;

import com.larfree.periscope.util.SpringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Blues
 * @date 2021/3/17 3:46 下午
 **/
public interface Storage {


    public Boolean set(String key, String value);

    public List multiGet();

    /**
     * 保存目录
     *
     * @param rootKey
     * @param indexKey
     * @return
     */
    public boolean setList(String rootKey, String indexKey);
}

