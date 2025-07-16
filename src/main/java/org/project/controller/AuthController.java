package org.project.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.project.dto.RegisterDTO;
import org.project.dto.request.LoginRequest;
import org.project.dto.response.Response;
import org.project.service.GoogleAuthService;
import org.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterDTO registerDTO) {
        Response response = userService.register(registerDTO);

        int status = response.getStatusCode();

        if (status < 100 || status > 599) {
            status = 500;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body,
                                             HttpServletResponse response) {
        return googleAuthService.loginWithGoogle(body, response);
    }
}
