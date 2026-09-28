package com.example.BloodBank.service;

import com.example.BloodBank.model.BloodUnit;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BloodUnitService {

    BloodUnit addBloodUnit(BloodUnit bloodUnit);

    List<BloodUnit> getAllBloodUnits();

    Optional<BloodUnit> getBloodUnitById(Long id);

    BloodUnit updateBloodUnit(Long id, BloodUnit bloodUnit);

    void deleteBloodUnit(Long id);

    Map<String, Long> getInventory();

    List<BloodUnit> getExpiringBloodUnits();
}