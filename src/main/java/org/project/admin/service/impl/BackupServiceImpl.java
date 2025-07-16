package org.project.admin.service.impl;

import org.project.admin.service.BackupService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class BackupServiceImpl implements BackupService {

    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final String DB = "swp391_new2";

    @Override
    public InputStream backup() throws IOException {
        String command = "mysqldump -u root -p123456 swp391_new2";
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);
        Process process = pb.start();

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println("[mysqldump-err] " + line);
                }
            } catch (IOException ignored) {}
        }).start();

        return process.getInputStream();
    }



    @Override
    public boolean restore(MultipartFile file) throws IOException, InterruptedException {
        File tempFile = File.createTempFile("restore", ".sql");
        file.transferTo(tempFile);

        String command = "mysql -u " + USER + " -p" + PASS + " " + DB;
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);

        Process process = pb.start();

        try (
                OutputStream os = process.getOutputStream();
                FileInputStream fis = new FileInputStream(tempFile)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }

        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String errLine;
            while ((errLine = errorReader.readLine()) != null) {
                System.err.println("[mysql-err] " + errLine);
            }
        }

        int exitCode = process.waitFor();
        tempFile.delete();
        return exitCode == 0;
    }


}
