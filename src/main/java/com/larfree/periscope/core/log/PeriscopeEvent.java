package com.larfree.periscope.core.log;

import com.larfree.periscope.core.log.Elements.QueueEvent;
import com.larfree.periscope.core.log.Elements.TimePointEvent;

/**
 * 后面有写成工厂方法的
 * @author Blues
 * @date 2021/3/16 10:04 下午
 **/
public class PeriscopeEvent {

    private static TimePointEvent timePointEvent = new TimePointEvent();
    private static QueueEvent queueEvent = new QueueEvent();

    public static QueueEvent getQueueEvent() {
        return queueEvent;
    }
    public static TimePointEvent getTimePointEvent() {
        return timePointEvent;
    }

}
