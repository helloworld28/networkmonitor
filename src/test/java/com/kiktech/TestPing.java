package com.kiktech;

import io.parallec.core.bean.ping.PingMeta;
import io.parallec.core.bean.ping.PingMode;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class TestPing {
    public static void main(String[] args) {
        PingMeta pingMeta = new PingMeta();
        pingMeta.setMode(PingMode.PROCESS);
        pingMeta.setNumRetries(1);
        pingMeta.setPingTimeoutMillis(500);
        boolean reachableByPingHelper = new TestPing().isReachableByPingHelper("192.168.1.252", pingMeta);
        System.out.println(reachableByPingHelper);
    }
    public boolean isReachableByPingHelper(String targetHost, PingMeta pingMeta) {
        try {

            if(pingMeta.getMode()==PingMode.INET_ADDRESS_REACHABLE_NEED_ROOT){
                InetAddress address = InetAddress.getByName(targetHost);
                return address.isReachable(pingMeta.getPingTimeoutMillis());
            }else{
                String cmd = "";
                String os = System.getProperty("os.name").toLowerCase();
                if (os.indexOf("win")>=0) {
                    // For Windows
                    cmd = "startPing -n 1 -w " + pingMeta.getPingTimeoutMillis() + " " + targetHost;
                } else {
                    // For Linux (-W) and OSX (-t)
                    String timeoutArg = os.indexOf("mac")>=0 ? "-t" : "-W";
                    cmd = "startPing -c 1 "
                            + timeoutArg
                            + " "+ (int) (pingMeta.getPingTimeoutMillis()/1000) + " "  + targetHost;
                }
                Process myProcess = Runtime.getRuntime().exec(cmd);
                myProcess.waitFor();
                if (myProcess.exitValue() == 0) {
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
