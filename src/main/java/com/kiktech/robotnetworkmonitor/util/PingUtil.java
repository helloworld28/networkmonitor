package com.kiktech.robotnetworkmonitor.util;

import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.ParallelClient;
import io.parallec.core.ParallelTask;
import io.parallec.core.ResponseOnSingleTask;
import io.parallec.core.bean.ping.PingMode;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jim
 * @date 2018/5/10
 */
public class PingUtil {

    private static void ping(ParallelClient pc) {
        ParallelTask task = pc.preparePing().setConcurrency(1500)
                .setTargetHostsFromList(Arrays.asList("192.168.1.194", "192.168.3.193", "192.168.3.190"))
                .setPingMode(PingMode.INET_ADDRESS_REACHABLE_NEED_ROOT)
                .setPingNumRetries(2)
                .setPingTimeoutMillis(500)
                .execute(new ParallecResponseHandler() {
                    public void onCompleted(ResponseOnSingleTask res,
                                            Map<String, Object> responseContext) {

                        System.out.println("*******" + res.toString());
                        System.out.println(String.format("++++%s:%s", res.getHost(), res.getStatusCode()));
                    }
                });
    }

    public static void main(String[] args) {
        ParallelClient pc = new ParallelClient();
        ping(pc);
    }
}
