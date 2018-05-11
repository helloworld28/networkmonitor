package com.kiktech.robotnetworkmonitor.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiktech.robotnetworkmonitor.model.HostNetworkLog;
import com.kiktech.robotnetworkmonitor.service.PingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jim
 * @date 2018/5/10
 */

@RestController
@RequestMapping("/pingControl")
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);
    private PingService pingService;

    @Value("${ping.hosts.file}")
    public String hostsFilePath;

    private List<String> hostList;

    @PostConstruct
    public void initHostData() throws IOException {
        if (StringUtils.isNotBlank(hostsFilePath)) {

            hostList = Files.readAllLines(Paths.get(hostsFilePath));

            hostList = hostList.stream()
                    .peek(host -> StringUtils.trim(host))
                    .filter(StringUtils::isNotBlank).collect(toList());
        }
    }

    @PostMapping("/startPing")
    public String startPing() {
        logger.info("startPing...");
        if (!hostList.isEmpty()) {
            pingService.startPing(hostList);
        } else {
            return "ERROR";
        }
        return "OK";
    }

    @PostMapping("/stopPing")
    public String stopPing() {
        logger.info("stopPing...");
        pingService.stopPing();
        return "OK";
    }


    @Autowired
    public void setPingService(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/getHostList")
    public List<String> getHostList() {
        return hostList;
    }

    @GetMapping("/getHostNetworkLogs")
    public String getHostNetworkLog(String host) {
        List<HostNetworkLog> hostNetworkLogs = pingService.getHostNetworkLogs(host);
        ObjectMapper objectMapper = new ObjectMapper();
        if (hostNetworkLogs != null) {
            List<String> logs = hostNetworkLogs.stream().map(hostNetworkLog -> {
                try {
                    return objectMapper.writeValueAsString(hostNetworkLog);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return "";
            }).collect(toList());
            return StringUtils.join(logs, "<br/>");
        } else {

            return "暂无数据";
        }
    }
}
