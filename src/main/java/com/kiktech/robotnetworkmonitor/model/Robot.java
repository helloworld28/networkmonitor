package com.kiktech.robotnetworkmonitor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Robot {
    private Long id;

    private String name;

    private String address;
    @JsonIgnore
    private Boolean isEnable;

    private String description;
    @JsonIgnore
    private Integer locationX;
    @JsonIgnore
    private Integer locationY;
    @JsonIgnore
    private Integer face;

    private boolean isRunning;


    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return is_enable
     */
    public Boolean getIsEnable() {
        return isEnable;
    }

    /**
     * @param isEnable
     */
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return location_x
     */
    public Integer getLocationX() {
        return locationX;
    }

    /**
     * @param locationX
     */
    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    /**
     * @return location_y
     */
    public Integer getLocationY() {
        return locationY;
    }

    /**
     * @param locationY
     */
    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    /**
     * @return face
     */
    public Integer getFace() {
        return face;
    }

    /**
     * @param face
     */
    public void setFace(Integer face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isEnable=" + isEnable +
                ", description='" + description + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", face=" + face +
                '}';
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean running) {
        isRunning = running;
    }
}