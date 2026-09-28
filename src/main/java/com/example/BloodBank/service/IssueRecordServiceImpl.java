package com.example.BloodBank.service;

import com.example.BloodBank.model.BloodUnit;
import com.example.BloodBank.model.IssueRecord;
import com.example.BloodBank.repo.BloodUnitRepository;
import com.example.BloodBank.repo.IssueRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueRecordServiceImpl implements IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final BloodUnitRepository bloodUnitRepository;

    public IssueRecordServiceImpl(IssueRecordRepository issueRecordRepository,
                                  BloodUnitRepository bloodUnitRepository) {
        this.issueRecordRepository = issueRecordRepository;
        this.bloodUnitRepository = bloodUnitRepository;
    }

    @Override
    public IssueRecord addIssueRecord(IssueRecord issueRecord) {
        return issueRecordRepository.save(issueRecord);
    }

    @Override
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordRepository.findAll();
    }

    @Override
    public Optional<IssueRecord> getIssueRecordById(Long id) {
        return issueRecordRepository.findById(id);
    }

    @Override
    public IssueRecord updateIssueRecord(Long id, IssueRecord issueRecord) {

        IssueRecord existingRecord = issueRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        existingRecord.setBloodGroup(issueRecord.getBloodGroup());
        existingRecord.setQuantity(issueRecord.getQuantity());
        existingRecord.setIssuedTo(issueRecord.getIssuedTo());
        existingRecord.setIssueDate(issueRecord.getIssueDate());

        return issueRecordRepository.save(existingRecord);
    }

    @Override
    public void deleteIssueRecord(Long id) {
        issueRecordRepository.deleteById(id);
    }

    @Override
    public IssueRecord issueBlood(IssueRecord issueRecord) {

        List<BloodUnit> availableUnits =
                bloodUnitRepository.findByBloodGroupAndStatus(
                        issueRecord.getBloodGroup(),
                        "AVAILABLE"
                );

        if (availableUnits.size() < issueRecord.getQuantity()) {
            throw new RuntimeException("Not enough blood units available");
        }

        // Mark required units as ISSUED
        for (int i = 0; i < issueRecord.getQuantity(); i++) {

            BloodUnit bloodUnit = availableUnits.get(i);

            bloodUnit.setStatus("ISSUED");

            bloodUnitRepository.save(bloodUnit);
        }

        // Save the issue record
        return issueRecordRepository.save(issueRecord);
    }
}