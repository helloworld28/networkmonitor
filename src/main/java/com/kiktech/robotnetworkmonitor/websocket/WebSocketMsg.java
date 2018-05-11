package com.kiktech.robotnetworkmonitor.websocket;

/**
 * WebSocket返回消息实体
 * Created by Jim on 2017/7/12.
 */
public class WebSocketMsg {

    private String  type;
    private Object data;

    public WebSocketMsg() {
    }

    public WebSocketMsg(String type, Object data) {
        this.type = type;
        this.data = data;
    }



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
