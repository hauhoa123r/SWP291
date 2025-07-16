package org.project.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.RegisterDTO;
import org.project.dto.response.Response;
import org.project.service.ForgotPasswordService;
import org.project.service.UserService;
import org.project.utils.ChangePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth-view")
@RequiredArgsConstructor
public class    AuthViewController {


    private final ForgotPasswordService forgotPasswordService;

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "redirectTo", required = false) String redirectTo, Model model) {
        model.addAttribute("redirectTo", redirectTo);
        return "frontend/login";
    }


    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              @RequestParam(required = false) String redirectTo,
                              Model model,
                              HttpServletResponse response) {
        try {
            String view = userService.login1(email, password, redirectTo, response);
            return view;

        } catch (Exception e) {
            model.addAttribute("error", "Email hoặc mật khẩu sai!");
            model.addAttribute("redirectTo", redirectTo);
            return "frontend/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "frontend/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("registerDTO") @Valid RegisterDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "frontend/register";
        }

        Response response = userService.register(dto);

        if (response.getStatusCode() != 201) {
            model.addAttribute("error", response.getMessage());
            return "frontend/register";
        }

        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "frontend/login";
    }


    @GetMapping("/forgot-password")
    public String showForgotPage() {
        return "frontend/forgot-email";
    }

    @PostMapping("/forgot-password")
    public String handleEmail(@RequestParam String email, Model model) {
        ResponseEntity<String> response = forgotPasswordService.sendOtp(email);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", response.getBody());
            return "redirect:/auth-view/verify-otp?email=" + email;
        } else {
            model.addAttribute("error", response.getBody());
            return "frontend/forgot-email";
        }
    }

    @GetMapping("/verify-otp")
    public String showOtpPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "frontend/verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam Integer otp,
                            Model model) {
        ResponseEntity<String> response = forgotPasswordService.verifyOtp(otp, email);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/auth-view/change-password?email=" + email;
        } else {
            model.addAttribute("error", response.getBody());
            model.addAttribute("email", email);
            return "frontend/verify-otp";
        }
    }

    @GetMapping("/change-password")
    public String showChangePassword(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "frontend/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        ChangePassword changePassword = new ChangePassword(password, confirmPassword);
        ResponseEntity<String> response = forgotPasswordService.changePassword(email, changePassword);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", response.getBody());
        } else {
            model.addAttribute("error", response.getBody());
        }

        model.addAttribute("email", email);
        return "frontend/change-password";
    }
}
