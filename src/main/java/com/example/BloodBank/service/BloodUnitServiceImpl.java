package com.example.BloodBank.service;

import com.example.BloodBank.model.BloodUnit;
import com.example.BloodBank.repo.BloodUnitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BloodUnitServiceImpl implements BloodUnitService {

    private final BloodUnitRepository bloodUnitRepository;

    public BloodUnitServiceImpl(BloodUnitRepository bloodUnitRepository) {
        this.bloodUnitRepository = bloodUnitRepository;
    }

    @Override
    public BloodUnit addBloodUnit(BloodUnit bloodUnit) {
        return bloodUnitRepository.save(bloodUnit);
    }

    @Override
    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitRepository.findAll();
    }

    @Override
    public Optional<BloodUnit> getBloodUnitById(Long id) {
        return bloodUnitRepository.findById(id);
    }

    @Override
    public BloodUnit updateBloodUnit(Long id, BloodUnit bloodUnit) {

        BloodUnit existingBloodUnit = bloodUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood unit not found"));

        existingBloodUnit.setDonation(bloodUnit.getDonation());
        existingBloodUnit.setBloodGroup(bloodUnit.getBloodGroup());
        existingBloodUnit.setCollectionDate(bloodUnit.getCollectionDate());
        existingBloodUnit.setExpiryDate(bloodUnit.getExpiryDate());
        existingBloodUnit.setStatus(bloodUnit.getStatus());

        return bloodUnitRepository.save(existingBloodUnit);
    }

    @Override
    public void deleteBloodUnit(Long id) {
        bloodUnitRepository.deleteById(id);
    }

    @Override
    public Map<String, Long> getInventory() {

        String[] bloodGroups = {
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"
        };

        Map<String, Long> inventory = new LinkedHashMap<>();

        for (String bloodGroup : bloodGroups) {

            long count = bloodUnitRepository
                    .findByBloodGroupAndStatus(bloodGroup, "AVAILABLE")
                    .size();

            inventory.put(bloodGroup, count);
        }

        return inventory;
    }
    @Override
    public List<BloodUnit> getExpiringBloodUnits() {

    LocalDate today = LocalDate.now();
    LocalDate sevenDaysLater = today.plusDays(7);

    return bloodUnitRepository.findByExpiryDateBetweenAndStatus(
            today,
            sevenDaysLater,
            "AVAILABLE"
        );
    }
}