package com.example.BloodBank.controller;

import com.example.BloodBank.model.IssueRecord;
import com.example.BloodBank.service.IssueRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    public IssueRecordController(IssueRecordService issueRecordService) {
        this.issueRecordService = issueRecordService;
    }

    @PostMapping
    public IssueRecord addIssueRecord(@RequestBody IssueRecord issueRecord) {
        return issueRecordService.addIssueRecord(issueRecord);
    }

    @GetMapping
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordService.getAllIssueRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueRecord> getIssueRecordById(@PathVariable Long id) {

        return issueRecordService.getIssueRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public IssueRecord updateIssueRecord(
            @PathVariable Long id,
            @RequestBody IssueRecord issueRecord) {

        return issueRecordService.updateIssueRecord(id, issueRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIssueRecord(@PathVariable Long id) {

        issueRecordService.deleteIssueRecord(id);

        return ResponseEntity.ok("Issue record deleted successfully");
    }
}