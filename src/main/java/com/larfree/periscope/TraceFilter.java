package com.zto.panda.common.periscope;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.dubbo.rpc.*;
import com.zto.panda.common.periscope.log.Interceptor;
import com.zto.panda.common.periscope.log.PeriscopeEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Blues
 * @description:
 * @create: 2022/03/12
 */
public class TraceFilter implements Filter {

    private String traceKey = "periscopeTraceKey";


    private Interceptor interceptor;

    @SneakyThrows
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {


        interceptor = new Interceptor();

        Result result;
        if (RpcContext.getContext().isConsumerSide()) {
            //如果是消费者,先读取下有没有上层传递过来的. 如果没有就生成一个
            String keyValue = RpcContext.getContext().getAttachments().get(traceKey);
            if (keyValue == null) {
                keyValue = RandomUtil.randomString(32);
            }
            System.out.println("periscopeTraceKey.consumer:" + keyValue);

            //serverKey 调用className + 调用函数 +  随机数
            String serverKey = keyValue + ":" + invoker.getInterface() + ":" + invocation.getMethodName()
                    + "=" + RandomUtil.randomString(10);

            interceptor.setRootTraceKey(keyValue);
            //捕获日志
            interceptor.start(serverKey + ":consumer");
            RpcContext.getContext().getAttachments().put(traceKey, serverKey);
            result = invoker.invoke(invocation);
            interceptor.end(serverKey + ":consumer");
            return result;

        } else {
            try {
                //根跟踪
                String rootTraceKey;
                // 如果是生产者，通过dubbo上下文获取TraceKey
                String keyValue = RpcContext.getContext().getAttachments().get(traceKey);
                if (keyValue == null) {
                    rootTraceKey = keyValue = RandomUtil.randomString(32);
                } else {
                    String[] keyValues = keyValue.split(":");
                    rootTraceKey = keyValues[0];
                }
                interceptor.setRootTraceKey(rootTraceKey);
                //捕获日志
                interceptor.start(keyValue + ":provider");
                result = invoker.invoke(invocation);
                interceptor.end(keyValue + ":provider");
                return result;
            } finally {

            }
        }
    }


}
