package org.project.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadToCloudinary(MultipartFile file, String folder);
}
