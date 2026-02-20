package com.votezylite.util;

import com.votezylite.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileStorageService {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    public String save(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            Path directory = Paths.get(uploadDir, folder);
            Files.createDirectories(directory);
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = directory.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (IOException e) {
            throw new ApiException("Could not save file: " + e.getMessage());
        }
    }
}
