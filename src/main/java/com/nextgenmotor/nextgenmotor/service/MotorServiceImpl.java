package com.nextgenmotor.nextgenmotor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextgenmotor.nextgenmotor.model.MotorStatus;
import com.nextgenmotor.nextgenmotor.repository.MotorStatusRepository;

@Service
public class MotorServiceImpl implements MotorService {

    @Autowired
    private MotorStatusRepository repo;

    @Override
    public MotorStatus getLatestStatus() {
        MotorStatus s = repo.findTopByOrderByLastUpdatedTimeDesc();
        if (s == null) {
            // return empty default so frontend doesn't crash
            s = new MotorStatus(false, null, null, System.currentTimeMillis());
            repo.save(s);
        }
        return s;
    }

    @Override
    public MotorStatus updateStatus(boolean motorOn, Integer waterLevel, String location) {
        MotorStatus existing = repo.findTopByOrderByLastUpdatedTimeDesc();
        if (existing == null) existing = new MotorStatus();

        existing.setMotorOn(motorOn);
        existing.setWaterLevel(waterLevel);
        existing.setLocation(location);
        existing.setLastUpdatedTime(System.currentTimeMillis());
        return repo.save(existing);
    }
}
