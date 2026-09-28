package com.example.BloodBank.repo;

import com.example.BloodBank.model.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, Long> {

    List<BloodUnit> findByBloodGroupAndStatus(String bloodGroup, String status);

    List<BloodUnit> findByExpiryDateBetweenAndStatus(
            LocalDate startDate,
            LocalDate endDate,
            String status
    );
}