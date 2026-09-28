package com.example.BloodBank.service;

import com.example.BloodBank.model.Donor;
import com.example.BloodBank.repo.DonationRepository;
import com.example.BloodBank.repo.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;

    public DonorServiceImpl(DonorRepository donorRepository,
                            DonationRepository donationRepository) {
        this.donorRepository = donorRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public Donor addDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }

    @Override
    public Donor updateDonor(Long id, Donor donor) {

        Donor existingDonor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        existingDonor.setName(donor.getName());
        existingDonor.setAge(donor.getAge());
        existingDonor.setBloodGroup(donor.getBloodGroup());
        existingDonor.setPhone(donor.getPhone());

        return donorRepository.save(existingDonor);
    }

    @Override
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    @Override
    public boolean checkEligibility(Long id) {

        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        // Check age
        if (donor.getAge() < 18 || donor.getAge() > 65) {
            return false;
        }

        // Find the donor's latest donation
        Optional<com.example.BloodBank.model.Donation> latestDonation =
                donationRepository.findTopByDonorOrderByDonationDateDesc(donor);

        // No previous donation
        if (latestDonation.isEmpty()) {
            return true;
        }

        // Check whether 3 months have passed
        LocalDate lastDonationDate =
                latestDonation.get().getDonationDate();

        LocalDate eligibleDate =
                lastDonationDate.plusMonths(3);

        return !LocalDate.now().isBefore(eligibleDate);
    }
}