package com.kiktech.robotnetworkmonitor.service;

import com.kiktech.robotnetworkmonitor.websocket.WebSocketMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Jim on 2017/9/6.
 */
@Component
public class MessagePushServiceImpl implements MessagePushService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void pushMessage(String topic, WebSocketMsg webSocketResMsg) {
        simpMessagingTemplate.convertAndSend(topic, webSocketResMsg);
    }
}
