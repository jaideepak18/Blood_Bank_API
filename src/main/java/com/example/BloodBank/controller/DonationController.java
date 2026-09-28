package com.example.BloodBank.controller;

import com.example.BloodBank.model.Donation;
import com.example.BloodBank.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public Donation addDonation(@Valid @RequestBody Donation donation) {
        return donationService.addDonation(donation);
    }

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {

        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Donation updateDonation(
            @PathVariable Long id,
            @RequestBody Donation donation) {

        return donationService.updateDonation(id, donation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonation(@PathVariable Long id) {

        donationService.deleteDonation(id);

        return ResponseEntity.ok("Donation deleted successfully");
    }
}