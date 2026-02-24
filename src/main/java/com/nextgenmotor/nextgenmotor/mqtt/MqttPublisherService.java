package com.nextgenmotor.nextgenmotor.mqtt;

public interface MqttPublisherService {
    void publishMotorControl(boolean motorOn);
}
