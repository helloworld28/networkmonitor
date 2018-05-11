package com.kiktech.robotnetworkmonitor.service;

import com.kiktech.robotnetworkmonitor.model.Robot;
import com.kiktech.robotnetworkmonitor.util.DOMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author Jim
 * @date 2018/5/10
 */
//@Service
public class RobotInfoService {
    private List<Robot> robots = Collections.EMPTY_LIST;
    private static final Logger logger = LoggerFactory.getLogger(RobotInfoService.class);


    private String robotInfoDir = "robotInfo";
    private String robotInfoFileName = "robot.xml";

    private Map<String, String> robotNameToAddressMap = new HashMap<>();
    private Map<String, Robot> robotNameToRobotMap = new HashMap<>();

    @PostConstruct
    public void loadRobotInfo() {
        try {
            Path path = Paths.get(robotInfoDir, robotInfoFileName).toAbsolutePath();
            File file = path.toFile();
            if (file.exists()) {
                robots = new DOMParser().parseToRobot(path.toAbsolutePath().toString());
                initRobotMap(robots);
                logger.info("加载机器人的信息成功![{}]", robots);

            } else {
                logger.warn("机器人信息文件不存在[{}]!", path.toAbsolutePath().toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    private void initRobotMap(List<Robot> robots) {
        robotNameToAddressMap = robots.stream().collect(toMap(Robot::getName, Robot::getAddress));
        robotNameToRobotMap = robots.stream().collect(toMap(Robot::getName, robot -> robot));
    }


    public List<Robot> getRobots() {
        return robots;
    }
}
