package com.larfree.periscope.log.Elements;

import java.util.Date;

/**
 * 日志的元素
 *
 * @author Blues
 * @date 2021/3/16 9:47 下午
 **/
public interface IEvent {
    public Object parse();

    default public Boolean log(String value) {
        System.out.println(value);
        return true;
    }

    default public Long getNowTimestamp() {
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }
}
