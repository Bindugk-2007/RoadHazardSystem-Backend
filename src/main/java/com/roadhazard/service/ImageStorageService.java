
package com.roadhazard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final Path uploadDirectory =
            Paths.get("uploads").toAbsolutePath().normalize();

    public ImageStorageService() throws IOException {
        Files.createDirectories(uploadDirectory);
    }

    // Upload and store image
    public String storeImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
            !(contentType.equals("image/jpeg") ||
              contentType.equals("image/png") ||
              contentType.equals("image/webp"))) {

            throw new IllegalArgumentException(
                    "Only JPG, PNG and WEBP images are allowed");
        }

        String extension;

        switch (contentType) {
            case "image/jpeg":
                extension = ".jpg";
                break;
            case "image/png":
                extension = ".png";
                break;
            case "image/webp":
                extension = ".webp";
                break;
            default:
                throw new IllegalArgumentException("Invalid image format");
        }

        String fileName = UUID.randomUUID() + extension;

        Path targetLocation =
                uploadDirectory.resolve(fileName).normalize();

        if (!targetLocation.startsWith(uploadDirectory)) {
            throw new SecurityException("Invalid file path");
        }

        Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
        );

        return "uploads/" + fileName;
    }

    // Retrieve an uploaded image
    public Path getImage(String imagePath) throws IOException {

        String fileName = Paths.get(imagePath)
                .getFileName().toString();

        Path imageLocation =
                uploadDirectory.resolve(fileName).normalize();

        if (!imageLocation.startsWith(uploadDirectory) ||
                !Files.exists(imageLocation)) {
            throw new NoSuchFileException("Image not found");
        }

        return imageLocation;
    }
}
