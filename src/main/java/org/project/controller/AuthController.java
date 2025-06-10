package org.project.controller;

import org.project.dto.request.LoginRequest;
import org.project.dto.response.Response;
import org.project.entity.UserEntity;
import org.project.repository.UserRepository;
import org.project.service.GoogleAuthService;
import org.project.service.GoogleTokenVerifierService;
import org.project.service.impl.IUserService;
import org.project.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    private final GoogleTokenVerifierService googleVerifier;
    private final UserRepository userRepo;
    private final JWTUtils jwtUtils;
    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    public AuthController(GoogleTokenVerifierService googleVerifier,
                          UserRepository userRepo,
                          JWTUtils jwtUtils) {
        this.googleVerifier = googleVerifier;
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserEntity user) {
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/google")

    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) {
        return googleAuthService.loginWithGoogle(body);
    }
}
