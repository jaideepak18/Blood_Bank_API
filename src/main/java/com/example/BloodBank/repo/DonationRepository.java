package com.example.BloodBank.repo;

import com.example.BloodBank.model.Donation;
import com.example.BloodBank.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Optional<Donation> findTopByDonorOrderByDonationDateDesc(Donor donor);
}