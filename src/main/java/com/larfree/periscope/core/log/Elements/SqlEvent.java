package com.larfree.periscope.core.log.Elements;

import java.util.HashMap;

/**
 * 获取数据库的sql
 *
 * @author Blues
 * @date 2021 /3/16 9:43 下午
 */
public class SqlEvent implements IEvent {

    public void log(String sql, String time, String from) {
        String message =
                "[" + getNowTimestamp() + "]"
                + " [periscope] [sqlEvent] [" + time + "] [" + from + "] : " + sql;
        log(message);
    }


    @Override
    public HashMap<String, Integer> parse() {
        return null;
    }


}
