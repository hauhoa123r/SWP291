package org.project.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.dto.RegisterDTO;

import org.project.dto.request.LoginRequest;
import org.project.dto.response.Response;
import org.project.entity.PatientEntity;
import org.project.entity.UserEntity;
import org.project.enums.FamilyRelationship;
import org.project.enums.PatientStatus;
import org.project.enums.UserRole;
import org.project.exception.OurException;
import org.project.repository.PatientRepository;
import org.project.repository.UserRepository;
import org.project.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Response register(RegisterDTO dto) {
        Response response = new Response();
        try {

            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new OurException("Vui lòng nhập email.");
            }

            if (!dto.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                throw new OurException("Email không hợp lệ.");
            }

            if (dto.getPassword() == null || dto.getPassword().isBlank()) {
                throw new OurException("Vui lòng nhập mật khẩu.");
            }

            if (dto.getPassword().length() < 6) {
                throw new OurException("Mật khẩu phải có ít nhất 6 ký tự.");
            }

            if (dto.getPhoneNumber() == null || dto.getPhoneNumber().isBlank()) {
                throw new OurException("Vui lòng nhập số điện thoại.");
            }

            if (!dto.getPhoneNumber().matches("^(03|05|07|08|09)\\d{8}$")) {
                throw new OurException("Số điện thoại không hợp lệ.");
            }

            if (dto.getFullName() == null || dto.getFullName().isBlank()) {
                throw new OurException("Vui lòng nhập họ và tên.");
            }

            if (dto.getAddress() == null || dto.getAddress().isBlank()) {
                throw new OurException("Vui lòng nhập địa chỉ.");
            }

            if (dto.getBirthdate() == null || dto.getBirthdate().isBlank()) {
                throw new OurException("Vui lòng chọn ngày sinh.");
            }

            if (dto.getGender() == null) {
                throw new OurException("Vui lòng chọn giới tính.");
            }

            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new OurException("Email đã tồn tại: " + dto.getEmail());
            }

            if (Date.valueOf(dto.getBirthdate()).after(new java.util.Date())) {
                throw new OurException("Ngày sinh không được lớn hơn ngày hiện tại.");
            }


            UserEntity user = new UserEntity();
            user.setEmail(dto.getEmail());
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setUserRole(UserRole.PATIENT);
            user.setIsVerified(true);
            UserEntity savedUser = userRepository.save(user);

            PatientEntity patient = new PatientEntity();
            patient.setUserEntity(savedUser);
            patient.setEmail(dto.getEmail());
            patient.setPhoneNumber(dto.getPhoneNumber());
            patient.setFullName(dto.getFullName());
            patient.setAddress(dto.getAddress());
            patient.setBirthdate(Date.valueOf(dto.getBirthdate()));
            patient.setPatientStatus(PatientStatus.ACTIVE);
            patient.setGender(dto.getGender());
            patient.setFamilyRelationship(FamilyRelationship.SELF);
            patientRepository.save(patient);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", savedUser.getId());
            userInfo.put("email", savedUser.getEmail());
            userInfo.put("phone", savedUser.getPhoneNumber());
            userInfo.put("fullname", patient.getFullName());
            userInfo.put("address", patient.getAddress());
            userInfo.put("birthdate", patient.getBirthdate());
            userInfo.put("role", savedUser.getUserRole());
            response.setData(userInfo);

            response.setStatusCode(201);
            response.setMessage("Register success");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during registration: " + e.getMessage());
            return response;
        }

        return response;
    }


    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User not found"));

            String token = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getUserRole().name());
            response.setExpirationTime("7 Days");
            response.setMessage("Login successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during login: " + e.getMessage());
        }
        return response;
    }

    public String login1(String email, String password, String redirectTo, HttpServletResponse response) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserEntity user = userRepository.findByEmail(email).orElseThrow();

//        if(!user.getIsVerified()) {
//            throw new OurException("Tài khoản chưa được xác minh hoặc đã bị vô hiệu hóa.");
//        }
//        if (!"INACTIVE".equals(user.getUserStatus())) {
//            throw new OurException("Tài khoản không hoạt động.");
//        }

        String token = jwtUtils.generateToken(user);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        if (redirectTo != null && !redirectTo.isBlank()) {
            return "redirect:" + redirectTo;
        }

        return switch (user.getUserRole()) {
            case ADMIN -> "redirect:http://localhost:3000/";
            case DOCTOR -> "redirect:/doctor";
            case PATIENT -> "redirect:/patient";
            case STAFF -> "redirect:/staff";
            default -> throw new RuntimeException("Unknown role.");
        };
    }
}
