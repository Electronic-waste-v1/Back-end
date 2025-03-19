package org.example.ewastev0_1.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.exception.FileStorageException;
import org.example.ewastev0_1.services.Interface.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class LocalFileStorageService implements FileStorageService {
    private final Path uploadDir = Paths.get("uploads/annonces");

    public LocalFileStorageService() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory");
        }
    }

    @Override
    public String storeFile(MultipartFile file, Integer annonceId) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path annonceDir = uploadDir.resolve(annonceId.toString());
            Files.createDirectories(annonceDir);
            Path destination = annonceDir.resolve(fileName);
            file.transferTo(destination);
            return "/uploads/annonces/" + annonceId + "/" + fileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file");
        }
    }

    @Override
    public void deleteFile(String url) {
        Path path = Paths.get(url);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", url, e);
        }
    }
}