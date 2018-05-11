package com.kiktech.robotnetworkmonitor.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Jim
 * @date 2018/5/10
 */
public class HostNetworkLog {
    private String host;
    private String connectedEvent;
    private Date createTime;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getConnectedEvent() {
        return connectedEvent;
    }

    public void setConnectedEvent(String connectedEvent) {
        this.connectedEvent = connectedEvent;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:sss",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public static final class HostNetworkLogBuilder {
        private String host;
        private String connectedEvent;
        private Date createTime;

        private HostNetworkLogBuilder() {
        }

        public static HostNetworkLogBuilder aHostNetworkLog() {
            return new HostNetworkLogBuilder();
        }

        public HostNetworkLogBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public HostNetworkLogBuilder withConnectedEvent(String connectedEvent) {
            this.connectedEvent = connectedEvent;
            return this;
        }

        public HostNetworkLogBuilder withCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public HostNetworkLog build() {
            HostNetworkLog hostNetworkLog = new HostNetworkLog();
            hostNetworkLog.setHost(host);
            hostNetworkLog.setConnectedEvent(connectedEvent);
            hostNetworkLog.setCreateTime(createTime);
            return hostNetworkLog;
        }
    }

    @Override
    public String toString() {
        return "HostNetworkLog{" +
                "host='" + host + '\'' +
                ", connectedEvent='" + connectedEvent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
