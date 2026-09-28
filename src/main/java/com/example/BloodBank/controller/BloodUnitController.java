package com.example.BloodBank.controller;

import com.example.BloodBank.model.BloodUnit;
import com.example.BloodBank.service.BloodUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blood-units")
public class BloodUnitController {

    private final BloodUnitService bloodUnitService;

    public BloodUnitController(BloodUnitService bloodUnitService) {
        this.bloodUnitService = bloodUnitService;
    }

    @PostMapping
    public BloodUnit addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        return bloodUnitService.addBloodUnit(bloodUnit);
    }

    @GetMapping
    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitService.getAllBloodUnits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodUnit> getBloodUnitById(@PathVariable Long id) {

        return bloodUnitService.getBloodUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/inventory")
    public Map<String, Long> getInventory() {
    return bloodUnitService.getInventory();
}

    @PutMapping("/{id}")
    public BloodUnit updateBloodUnit(
            @PathVariable Long id,
            @RequestBody BloodUnit bloodUnit) {

        return bloodUnitService.updateBloodUnit(id, bloodUnit);
    }
    @GetMapping("/expiring")
    public List<BloodUnit> getExpiringBloodUnits() {
        return bloodUnitService.getExpiringBloodUnits();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBloodUnit(@PathVariable Long id) {

        bloodUnitService.deleteBloodUnit(id);

        return ResponseEntity.ok("Blood unit deleted successfully");
    }
}