package com.kiktech.robotnetworkmonitor.service;


import com.kiktech.robotnetworkmonitor.websocket.WebSocketMsg;

/**
 * Created by Jim on 2017/9/6.
 */
public interface MessagePushService {

    void pushMessage(String topic, WebSocketMsg webSocketResMsg);
}
