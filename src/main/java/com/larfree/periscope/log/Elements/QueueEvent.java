package com.larfree.periscope.log.Elements;

import java.util.Date;
import java.util.HashMap;

/**
 * The type Time point event.
 *
 * @author Blues
 * @date 2021 /3/16 9:43 下午
 */
public class QueueEvent implements IEvent {
    /**
     * Start.
     *
     * @param key the key
     */
    public void send(String key,String value) {
        log( "[" + getNowTimestamp() + "] [periscope] [queueEvent] ["+key+"] : "+value);
    }


    @Override
    public HashMap<String, Integer> parse() {
        return null;
    }




}
