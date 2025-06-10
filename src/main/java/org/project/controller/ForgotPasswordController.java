package org.project.controller;



import jakarta.mail.MessagingException;
import org.project.entity.ForgotPassword;
import org.project.entity.UserEntity;
import org.project.repository.ForgotPasswordRepository;
import org.project.repository.UserRepository;
import org.project.service.MailService;
import org.project.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordController(UserRepository userRepository, MailService mailService, PasswordEncoder passwordEncoder, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    //send email for email verification
    @PostMapping("/verify/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

        int otp = otpGenerator();
        try {
            mailService.senEmail("Change Password",
                    "This is the OTP for your Forgot Password request : " + otp,
                    user.getEmail());

        } catch (MessagingException | UnsupportedEncodingException e) {

            throw new IllegalArgumentException(e);
        }

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user)
                .build();

        forgotPasswordRepository.save(forgotPassword);
        return ResponseEntity.ok("Email sent for verification !");

    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Please provide an valid email!" + email));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!",  HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP has expired!");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword,
                                                 @PathVariable String email){
        if(!Objects.equals(changePassword.password(), changePassword.confirmPassword())){
            return new ResponseEntity<>("Please enter the password again!",  HttpStatus.EXPECTATION_FAILED);
        }

        String encodePassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodePassword);
        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }



}
