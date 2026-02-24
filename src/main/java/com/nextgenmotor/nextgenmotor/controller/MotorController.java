package com.nextgenmotor.nextgenmotor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nextgenmotor.nextgenmotor.model.MotorStatus;
import com.nextgenmotor.nextgenmotor.mqtt.MqttPublisherService;
import com.nextgenmotor.nextgenmotor.service.MotorService;

@RestController
@RequestMapping("/api/motor")
@CrossOrigin(origins = "*")
public class MotorController {

    @Autowired
    private MotorService service;

    @Autowired
    private MqttPublisherService mqttPublisher;

    @GetMapping("/status")
    public MotorStatus getStatus() {
        return service.getLatestStatus();
    }

    // Optional: update directly (useful for testing)
    @PostMapping("/update")
    public MotorStatus updateStatus(@RequestParam boolean motorOn,
                                    @RequestParam(required = false) Integer waterLevel,
                                    @RequestParam(required = false) String location) {
        return service.updateStatus(motorOn, waterLevel, location);
    }

    // This sends MQTT control message (app -> backend -> mqtt -> esp)
    @PostMapping("/control")
    public String controlMotor(@RequestParam boolean motorOn) {
        mqttPublisher.publishMotorControl(motorOn);
        return motorOn ? "Motor ON (published)" : "Motor OFF (published)";
    }
}
