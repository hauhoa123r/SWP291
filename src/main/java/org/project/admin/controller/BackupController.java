package org.project.admin.controller;

import org.project.admin.service.BackupService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @GetMapping("/download")
    public void backupDatabase(HttpServletResponse response) throws IOException {
        String backupFile = "backup-" + java.time.LocalDateTime.now().toString().replace(":", "-") + ".sql";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + backupFile);

        try (InputStream is = backupService.backup();
             OutputStream os = response.getOutputStream()) {
            is.transferTo(os);
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<?> restoreDatabase(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        boolean ok = backupService.restore(file);
        if (ok)
            return ResponseEntity.ok("Restore thành công!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Restore thất bại!");
    }
}

