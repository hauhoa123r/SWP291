package org.project.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface BackupService {
    InputStream backup() throws IOException;
    boolean restore(MultipartFile file) throws IOException, InterruptedException;
}
