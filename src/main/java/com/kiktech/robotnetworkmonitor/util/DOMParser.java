package com.kiktech.robotnetworkmonitor.util;

import com.kiktech.robotnetworkmonitor.model.Robot;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 2017/11/13.
 */
public class DOMParser {
    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public Document parse(String filePath) {
        Document document = null;
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(filePath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public List<Robot> parseToRobot(String filePath) {
        DOMParser domParser = new DOMParser();
        Document dom = domParser.parse(filePath);
        NodeList robotNodeList = dom.getElementsByTagName("Robot");
        List<Robot> robotList = new ArrayList<>();
        if (robotNodeList.getLength() > 0) {
            for (int i = 0; i < robotNodeList.getLength(); i++) {
                Node node = robotNodeList.item(i);
                NamedNodeMap attributes = node.getAttributes();
                String name = attributes.getNamedItem("name").getNodeValue();
                String ip = attributes.getNamedItem("ip").getNodeValue();
                String port = attributes.getNamedItem("port").getNodeValue();
                Robot robot = new Robot();
                robot.setName(name);
                robot.setAddress(String.format("%s:%s", ip, port));
                robotList.add(robot);
            }
        }
        return robotList;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
