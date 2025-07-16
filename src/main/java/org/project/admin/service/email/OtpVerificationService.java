package org.project.admin.service.email;

import org.project.admin.entity.OtpVerification;
import org.project.admin.repository.OtpVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {
    private final OtpVerificationRepository otpRepo;
    private final EmailService emailService;

    public void sendOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        OtpVerification entity = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .createdAt(LocalDateTime.now())
                .verified(false)
                .build();
        otpRepo.save(entity);
        emailService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        OtpVerification verification = otpRepo.findByEmailAndOtpAndVerifiedFalse(email, otp)
                .filter(v -> v.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(10))) // hết hạn sau 10 phút
                .orElseThrow(() -> new RuntimeException("Mã OTP không hợp lệ hoặc đã hết hạn"));

        verification.setVerified(true);
        otpRepo.save(verification);
        return true;
    }

    public boolean isEmailVerified(String email) {
        return otpRepo.findAll().stream()
                .anyMatch(v -> v.getEmail().equals(email) && v.isVerified());
    }
}
