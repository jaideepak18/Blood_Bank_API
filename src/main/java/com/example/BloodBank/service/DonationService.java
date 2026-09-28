package com.example.BloodBank.service;

import com.example.BloodBank.model.Donation;

import java.util.List;
import java.util.Optional;

public interface DonationService {

    Donation addDonation(Donation donation);

    List<Donation> getAllDonations();

    Optional<Donation> getDonationById(Long id);

    Donation updateDonation(Long id, Donation donation);

    void deleteDonation(Long id);
}