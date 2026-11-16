package com.roadhazard.controller;

import com.roadhazard.model.Hazard;
import com.roadhazard.repository.HazardRepository;
import com.roadhazard.service.ImageStorageService;
import com.roadhazard.exception.ResourceNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/hazards")
@CrossOrigin
public class ImageController {

    private final HazardRepository hazardRepository;
    private final ImageStorageService imageStorageService;

    public ImageController(
            HazardRepository hazardRepository,
            ImageStorageService imageStorageService) {

        this.hazardRepository = hazardRepository;
        this.imageStorageService = imageStorageService;
    }

    // Get hazard by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hazard> getHazardById(
            @PathVariable Long id) {

        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "No hazard found with ID: " + id
                    ));

        return ResponseEntity.ok(hazard);
    }

    // Upload image
    @PostMapping("/{id}/image")
    public ResponseEntity<Hazard> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "No hazard found with ID: " + id
                    ));

        String imagePath = imageStorageService.storeImage(file);

        hazard.setImagePath(imagePath);

        Hazard updatedHazard = hazardRepository.save(hazard);

        return ResponseEntity.ok(updatedHazard);
    }

    // Retrieve uploaded image
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(
            @PathVariable Long id) throws IOException {

        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "No hazard found with ID: " + id
                    ));

        if (hazard.getImagePath() == null ||
                hazard.getImagePath().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Path imagePath =
                imageStorageService.getImage(hazard.getImagePath());

        Resource resource = new UrlResource(imagePath.toUri());

        String fileName = imagePath.getFileName().toString();

        MediaType mediaType = fileName.endsWith(".png")
                ? MediaType.IMAGE_PNG
                : fileName.endsWith(".webp")
                ? MediaType.parseMediaType("image/webp")
                : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}