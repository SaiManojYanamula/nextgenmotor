package com.nextgenmotor.nextgenmotor.service;

import com.nextgenmotor.nextgenmotor.model.MotorStatus;

public interface MotorService {
    MotorStatus getLatestStatus();
    MotorStatus updateStatus(boolean motorOn, Integer waterLevel, String location);
}
