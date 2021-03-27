package com.larfree.periscope.core.storage;

/**
 * @author Blues
 * @date 2021/3/17 3:46 下午
 **/
public interface Storage {


    public Boolean set(String key, String value);


    /**
     * 保存目录
     *
     * @param rootKey
     * @param indexKey
     * @param action
     * @return
     */
    public boolean setList(String rootKey, String indexKey, String action);
}

