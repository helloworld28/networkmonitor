package com.kiktech.robotnetworkmonitor.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jim
 * @date 2018/5/10
 */
public class HostReadService {

    public static List<String> reaadHost() {
        Path path = Paths.get("D:/temp/hosts.txt" );
        try {
            List<String> strings = Files.readAllLines(path);
            System.out.println(strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
