package com.example.BloodBank.service;

import com.example.BloodBank.model.Donor;

import java.util.List;
import java.util.Optional;

public interface DonorService {

    Donor addDonor(Donor donor);

    List<Donor> getAllDonors();

    Optional<Donor> getDonorById(Long id);

    Donor updateDonor(Long id, Donor donor);

    void deleteDonor(Long id);

    boolean checkEligibility(Long id);
}