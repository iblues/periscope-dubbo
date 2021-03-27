package com.larfree.periscope.core.log;

import cn.hutool.core.util.RandomUtil;
import com.larfree.periscope.core.storage.StorageFactory;
import com.larfree.periscope.core.storage.Storage;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Blues
 * @date 2021/3/15 11:32 上午
 **/
public class Interceptor {
    //用于捕获日志
    private PrintStream previousConsole;
    private Monitor newConsole;


    private Storage storage;


    private String rootTraceKey = "";


    private String traceKey;

    public static String getRootTrackKey(String type) {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "null";
        }
        //时间戳+http+ip+随机
        return System.currentTimeMillis() + "-" + type + "-" + ip + "-" + RandomUtil.randomString(16);
    }

    public Interceptor() {
        storage = StorageFactory.getInstance();
    }

    public void start(String traceKey) {
        //在redis先把追踪链路记录
        saveIndex(rootTraceKey, traceKey, "start");
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

    public void end(String traceKey) {
        PeriscopeEvent.getTimePointEvent().end(traceKey);
        // 设置回之前的输出
        System.setOut(previousConsole);
        System.setErr(previousConsole);
        //输出新控制台的
        String out = newConsole.toString();
        //回写到Index方便统计时间
        saveIndex(rootTraceKey, traceKey, "end");
        //写入到redis以供调用
        saveDetail(traceKey, out);
    }

    /**
     * 记录到list 作为目录
     *
     * @param RootKey
     * @param indexKey
     */
    private boolean saveIndex(String RootKey, String indexKey, String action) {
        return storage.setList(RootKey, indexKey, action);
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
