package com.roadhazard.repository;

import com.roadhazard.model.Hazard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HazardRepository extends JpaRepository<Hazard, Long> {

    // Display complaints according to highest priority
    List<Hazard> findAllByOrderByPriorityScoreDesc();

    // Search complaints using location
    List<Hazard> findByLocationContainingIgnoreCase(String location);

    // Find complaints according to status
    List<Hazard> findByStatusIgnoreCase(String status);

    // Find complaints according to severity
    List<Hazard> findBySeverityIgnoreCase(String severity);
}