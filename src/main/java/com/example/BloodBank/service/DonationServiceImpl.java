package com.example.BloodBank.service;

import com.example.BloodBank.model.BloodUnit;
import com.example.BloodBank.model.Donation;
import com.example.BloodBank.model.Donor;
import com.example.BloodBank.repo.BloodUnitRepository;
import com.example.BloodBank.repo.DonationRepository;
import com.example.BloodBank.repo.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final BloodUnitRepository bloodUnitRepository;
    private final DonorRepository donorRepository;

    public DonationServiceImpl(DonationRepository donationRepository,
                            BloodUnitRepository bloodUnitRepository,
                            DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.bloodUnitRepository = bloodUnitRepository;
        this.donorRepository = donorRepository;
    }

    @Override
    public Donation addDonation(Donation donation) {

        // Find the actual donor from the database
        Long donorId = donation.getDonor().getId();

        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        // Set the actual donor
        donation.setDonor(donor);

        // Save donation
        Donation savedDonation = donationRepository.save(donation);

        // Create blood units
        for (int i = 0; i < donation.getQuantity(); i++) {

            BloodUnit bloodUnit = new BloodUnit();

            bloodUnit.setDonation(savedDonation);
            bloodUnit.setBloodGroup(donor.getBloodGroup());
            bloodUnit.setCollectionDate(donation.getDonationDate());

            // Simple 35-day expiry for this academic project
            bloodUnit.setExpiryDate(
                    donation.getDonationDate().plusDays(35)
            );

            bloodUnit.setStatus("AVAILABLE");

            bloodUnitRepository.save(bloodUnit);
        }

        return savedDonation;
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    @Override
    public Donation updateDonation(Long id, Donation donation) {

        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        existingDonation.setDonor(donation.getDonor());
        existingDonation.setDonationDate(donation.getDonationDate());
        existingDonation.setQuantity(donation.getQuantity());

        return donationRepository.save(existingDonation);
    }

    @Override
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}