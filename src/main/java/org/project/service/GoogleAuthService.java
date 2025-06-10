package org.project.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.project.entity.UserEntity;
import org.project.repository.UserRepository;
import org.project.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class GoogleAuthService {

    private final GoogleTokenVerifierService googleVerifier;
    private final UserRepository userRepo;
    private final JWTUtils jwtUtils;

    @Autowired
    public GoogleAuthService(GoogleTokenVerifierService googleVerifier, UserRepository userRepo, JWTUtils jwtUtils) {
        this.googleVerifier = googleVerifier;
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> loginWithGoogle(Map<String, String> body) {
        String token = body.get("token");
        try {
            Payload payload = googleVerifier.verify(token);
            if (payload == null) {

                return ResponseEntity.status(401).body("Invalid Google token");
            }
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            UserEntity user = userRepo.findByEmail(email).orElseGet(() -> {
                UserEntity newUser = new UserEntity();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setPasswordHash(UUID.randomUUID().toString());
                newUser.setRole("USER");
                UserEntity savedUser = userRepo.save(newUser);
                return savedUser;
            });
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password("") // JWT chỉ dùng username
                    .roles(user.getRole())
                    .build();
            String appToken = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok().body(Collections.singletonMap("token", appToken));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Google Login failed: " + e.getMessage());
        }
    }
}
