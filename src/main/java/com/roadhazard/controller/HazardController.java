
package com.roadhazard.controller;

import com.roadhazard.model.Hazard;
import com.roadhazard.service.HazardService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hazards")
@CrossOrigin
public class HazardController {

    private final HazardService hazardService;

    public HazardController(HazardService hazardService) {
        this.hazardService = hazardService;
    }

    // Register a new road hazard complaint with validation
    @PostMapping
    public ResponseEntity<Hazard> addHazard(
            @Valid @RequestBody Hazard hazard) {

        return ResponseEntity.ok(
                hazardService.addHazard(hazard));
    }

    // Get all complaints
    @GetMapping
    public ResponseEntity<List<Hazard>> getAllHazards() {

        return ResponseEntity.ok(
                hazardService.getAllHazards());
    }

    // Search complaints by location
    @GetMapping("/search")
    public ResponseEntity<List<Hazard>> searchHazards(
            @RequestParam String location) {

        return ResponseEntity.ok(
                hazardService.searchByLocation(location));
    }

    // Filter complaints by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Hazard>> getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                hazardService.getHazardsByStatus(status));
    }

    // Filter complaints by severity
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<Hazard>> getBySeverity(
            @PathVariable String severity) {

        return ResponseEntity.ok(
                hazardService.getHazardsBySeverity(severity));
    }

    // Update complaint status
    @PutMapping("/{id}/status")
    public ResponseEntity<Hazard> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                hazardService.updateStatus(id, status));
    }

    // Delete complaint
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHazard(
            @PathVariable Long id) {

        hazardService.deleteHazard(id);

        return ResponseEntity.ok(
                "Hazard complaint deleted successfully");
    }
}
