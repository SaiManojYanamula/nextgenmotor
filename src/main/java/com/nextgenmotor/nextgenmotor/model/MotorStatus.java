package com.nextgenmotor.nextgenmotor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "motor_status")
public class MotorStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // keep primitive to avoid null checks; default false = off
    @Column(name = "motor_on")
    private boolean motorOn;

    @Column(name = "water_level")
    private Integer waterLevel; // nullable ok

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "last_updated_time")
    private Long lastUpdatedTime;

    public MotorStatus() {}

    public MotorStatus(boolean motorOn, Integer waterLevel, String location, Long lastUpdatedTime) {
        this.motorOn = motorOn;
        this.waterLevel = waterLevel;
        this.location = location;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isMotorOn() { return motorOn; }
    public void setMotorOn(boolean motorOn) { this.motorOn = motorOn; }

    public Integer getWaterLevel() { return waterLevel; }
    public void setWaterLevel(Integer waterLevel) { this.waterLevel = waterLevel; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Long getLastUpdatedTime() { return lastUpdatedTime; }
    public void setLastUpdatedTime(Long lastUpdatedTime) { this.lastUpdatedTime = lastUpdatedTime; }
}
