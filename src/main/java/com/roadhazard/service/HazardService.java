package com.roadhazard.service;

import com.roadhazard.model.Hazard;
import com.roadhazard.repository.HazardRepository;
import com.roadhazard.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HazardService {

    private final HazardRepository hazardRepository;

    public HazardService(HazardRepository hazardRepository) {
        this.hazardRepository = hazardRepository;
    }

    // Register a new complaint
    public Hazard addHazard(Hazard hazard) {
        hazard.setPriorityScore(calculatePriority(hazard.getSeverity()));
        hazard.setStatus("Pending");
        return hazardRepository.save(hazard);
    }

    // Calculate priority based on severity
    private int calculatePriority(String severity) {

        if (severity == null) {
            throw new IllegalArgumentException("Severity is required");
        }

        switch (severity.trim().toLowerCase()) {
            case "high":
                return 100;
            case "medium":
                return 60;
            case "low":
                return 30;
            default:
                throw new IllegalArgumentException(
                    "Severity must be High, Medium or Low"
                );
        }
    }

    // Display all complaints by priority
    public List<Hazard> getAllHazards() {
        return hazardRepository.findAllByOrderByPriorityScoreDesc();
    }

    // Search complaints by location
    public List<Hazard> searchByLocation(String location) {
        return hazardRepository.findByLocationContainingIgnoreCase(location);
    }

    // Filter complaints by status
    public List<Hazard> getHazardsByStatus(String status) {
        return hazardRepository.findByStatusIgnoreCase(status);
    }

    // Filter complaints by severity
    public List<Hazard> getHazardsBySeverity(String severity) {
        return hazardRepository.findBySeverityIgnoreCase(severity);
    }

    // Update complaint status
    public Hazard updateStatus(Long id, String status) {

        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "No hazard found with ID: " + id
                    ));

        if (status == null ||
            !List.of("Pending", "In Progress", "Resolved")
                .contains(status.trim())) {
            throw new IllegalArgumentException("Invalid complaint status");
        }

        hazard.setStatus(status.trim());
        return hazardRepository.save(hazard);
    }

    // Delete complaint
    public void deleteHazard(Long id) {

        if (!hazardRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "No hazard found with ID: " + id
            );
        }

        hazardRepository.deleteById(id);
    }
}