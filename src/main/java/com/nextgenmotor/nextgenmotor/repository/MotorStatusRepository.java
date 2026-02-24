package com.nextgenmotor.nextgenmotor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nextgenmotor.nextgenmotor.model.MotorStatus;

public interface MotorStatusRepository extends JpaRepository<MotorStatus, Long> {
    MotorStatus findTopByOrderByLastUpdatedTimeDesc();
}
