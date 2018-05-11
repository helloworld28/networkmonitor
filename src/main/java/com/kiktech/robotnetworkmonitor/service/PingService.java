package com.kiktech.robotnetworkmonitor.service;

import com.kiktech.robotnetworkmonitor.model.HostNetworkLog;

import java.util.List;

/**
 * @author Jim
 * @date 2018/5/10
 */
public interface PingService {

    void startPing(List<String> hosts);
    void stopPing();

    List<HostNetworkLog> getHostNetworkLogs(String host);
}
