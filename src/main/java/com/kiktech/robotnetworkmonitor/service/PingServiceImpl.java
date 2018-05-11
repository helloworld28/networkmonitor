package com.kiktech.robotnetworkmonitor.service;

import com.google.common.collect.Maps;
import com.kiktech.robotnetworkmonitor.model.HostNetworkLog;
import com.kiktech.robotnetworkmonitor.websocket.WebSocketMsg;
import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.ParallelClient;
import io.parallec.core.ResponseOnSingleTask;
import io.parallec.core.bean.ping.PingMode;
import io.parallec.core.util.DaemonThreadFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2018/5/10
 */
@Service
public class PingServiceImpl implements PingService {

    private static final Logger logger = LoggerFactory.getLogger(PingServiceImpl.class);

    private Map<String, List<HostNetworkLog>> hostNetWorkLogs = Maps.newHashMap();
    private Map<String, String> hostCurrentStatus = Maps.newHashMap();
    private ScheduledExecutorService scheduler;
    private final List<String> hosts = new ArrayList<>();
    private MessagePushService messagePushService;
    private ParallecResponseHandler responseHandler = createResponseHandler();

    {
        ParallelClient pc = new ParallelClient();
        Runnable pingWork = initPingWork(pc);
        initScheduler(pingWork);
    }

    private Runnable initPingWork(ParallelClient pc) {
        return () -> {
            if (!hosts.isEmpty()) {
                pc.preparePing().setConcurrency(1500)
                        .setTargetHostsFromList(hosts)
                        .setPingMode(PingMode.PROCESS)
                        .setPingNumRetries(1)
                        .setPingTimeoutMillis(100)
                        .execute(responseHandler);
            }

        };
    }

    private void initScheduler(Runnable pingWork) {
        scheduler = Executors.newSingleThreadScheduledExecutor(DaemonThreadFactory.getInstance());
        scheduler.scheduleWithFixedDelay(
                pingWork,
                2000,
                1000,
                TimeUnit.MILLISECONDS
        );
    }


    @Override
    public void startPing(List<String> addHosts) {
        synchronized (hosts) {
            hosts.clear();
            hostNetWorkLogs.clear();
            hostCurrentStatus.clear();
            hosts.addAll(addHosts);
        }
    }

    @Override
    public void stopPing() {
        synchronized (hosts) {
            hosts.clear();
        }
    }

    @Override
    public List<HostNetworkLog> getHostNetworkLogs(String host) {
        return hostNetWorkLogs.get(host);
    }

    private void pushWebsocketMessage(HostNetworkLog hostNetworkLog) {
        logger.info("pushWebsocketMessage[{}]", hostNetworkLog);
        messagePushService.pushMessage("/topic/networkEvent", new WebSocketMsg("show", hostNetworkLog));
    }

    @Autowired
    public void setMessagePushService(MessagePushService messagePushService) {
        this.messagePushService = messagePushService;
    }

    private ParallecResponseHandler createResponseHandler() {
        return new ParallecResponseHandler() {
            @Override
            public void onCompleted(ResponseOnSingleTask res,
                                    Map<String, Object> responseContext) {

                String preStatus = hostCurrentStatus.get(res.getHost());
                String currentStatusCode = res.getStatusCode();
                if (!StringUtils.equals(preStatus, currentStatusCode)) {
                    logger.info("Host[{}]网络发生变化[{}]", res.getHost(), currentStatusCode);
                    HostNetworkLog hostNetworkLog = HostNetworkLog.HostNetworkLogBuilder
                            .aHostNetworkLog()
                            .withHost(res.getHost())
                            .withConnectedEvent(currentStatusCode)
                            .withCreateTime(new Date())
                            .build();
                    hostCurrentStatus.put(res.getHost(), currentStatusCode);
                    List<HostNetworkLog> hostNetworkLogs = hostNetWorkLogs.computeIfAbsent(res.getHost(), key -> new ArrayList<>());
                    hostNetworkLogs.add(hostNetworkLog);
                    pushWebsocketMessage(hostNetworkLog);
                }
            }
        };
    }
}
