package com.example.BloodBank.repo;

import com.example.BloodBank.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    
}