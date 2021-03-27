package com.larfree.periscope.core.log.Elements;

import java.util.HashMap;

/**
 * The type Time point event.
 *
 * @author Blues
 * @date 2021 /3/16 9:43 下午
 */
public class TimePointEvent implements IEvent {
    /**
     * Start.
     *
     * @param key the key
     */
    public void start(String key) {
        log("["+getNowTimestamp()+"] [periscope] [timePointEvent] ["+key+"] : start");
    }


    /**
     * End.
     *
     * @param key the key
     */
    public void end(String key) {
        log("["+getNowTimestamp()+"] [periscope] [timePointEvent] ["+key+"] : end");
    }

    @Override
    public HashMap<String, Integer> parse() {
        return null;
    }


}
