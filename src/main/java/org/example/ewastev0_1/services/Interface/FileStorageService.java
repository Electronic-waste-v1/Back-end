package org.example.ewastev0_1.services.Interface;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, Integer annonceId);
    void deleteFile(String url);
}