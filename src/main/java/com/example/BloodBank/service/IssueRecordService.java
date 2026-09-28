package com.example.BloodBank.service;

import com.example.BloodBank.model.IssueRecord;

import java.util.List;
import java.util.Optional;

public interface IssueRecordService {

    IssueRecord addIssueRecord(IssueRecord issueRecord);

    List<IssueRecord> getAllIssueRecords();

    Optional<IssueRecord> getIssueRecordById(Long id);

    IssueRecord updateIssueRecord(Long id, IssueRecord issueRecord);

    void deleteIssueRecord(Long id);

    IssueRecord issueBlood(IssueRecord issueRecord);
}