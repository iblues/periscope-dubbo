package com.larfree.periscope.log;

import com.larfree.periscope.storage.RedisStorage;

import java.io.PrintStream;

/**
 * @author Blues
 * @date 2021/3/15 11:32 上午
 **/
public class Interceptor {
    //用于捕获日志
    private PrintStream previousConsole;
    private Monitor newConsole;

    //默认当前只支持redis
    private String storeType = "redis";
    private RedisStorage storage;


    private String rootTraceKey = "";


    private String traceKey;

    public Interceptor() {
        switch (storeType) {
            case "redis":
            default:
                storage = new RedisStorage();
                break;
        }
    }


    public void start(String traceKey) {
        //在redis先把追踪链路记录
        saveIndex(rootTraceKey, traceKey);
        //保存之前的out
        previousConsole = System.out;
        //设置代理输出, 方便拿取消息
        newConsole = new Monitor(previousConsole);
        PrintStream newPrintStream = new PrintStream(newConsole);
        System.setOut(newPrintStream);
        System.setErr(newPrintStream);
        //记录时间点, 方便后面算时间
        PeriscopeEvent.getTimePointEvent().start(traceKey);
    }

    public String getTraceKey() {
        return traceKey;
    }

    public void setTraceKey(String traceKey) {
        this.traceKey = traceKey;
    }


    public void setRootTraceKey(String rootTraceKey) {
        System.out.println("PeriscopeRootKey" + rootTraceKey);
        this.rootTraceKey = rootTraceKey;
    }

    public void end(String trackKey) {
        PeriscopeEvent.getTimePointEvent().end(trackKey);
        // 设置回之前的输出
        System.setOut(previousConsole);
        System.setErr(previousConsole);
        //输出新控制台的
        String out = newConsole.toString();
        //写入到redis以供调用
        saveDetail(trackKey, out);
    }

    /**
     * 记录到list 作为目录
     *
     * @param RootKey
     * @param indexKey
     */
    private boolean saveIndex(String RootKey, String indexKey) {
        return storage.setList(RootKey, indexKey);
    }

    /**
     * 存更详细的
     *
     * @param key
     * @param value
     */
    private boolean saveDetail(String key, String value) {
        return storage.set(key, value);
    }

}
