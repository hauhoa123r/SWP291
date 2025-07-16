package org.project.security;

import org.project.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cấu hình các endpoint công khai và các quyền truy cập
        String[] publicEndpoints = {
                "/auth/**",        // Cho phép login và đăng ký mà không cần xác thực
                "/auth-view/**",   // Nếu có trang view nào cần truy cập công khai
                "/auth/google",    // Google OAuth
                "/assets/**",      // Tài nguyên tĩnh như CSS, JS
                "/css/**",
                "/js/**",
                "/images/**",
                "/vendor/**",
                "/forgotPassword/**" // Endpoint quên mật khẩu
        };

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints).permitAll()  // Cho phép các endpoints công khai
                        .requestMatchers("/api/**").permitAll() // Các API yêu cầu không xác thực
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Chỉ cho phép Admin truy cập vào /admin/**
                        .requestMatchers("/doctor/**").hasRole("DOCTOR")  // Chỉ cho phép Doctor truy cập vào /doctor/**
                        .requestMatchers("/patient/**").hasRole("PATIENT")  // Chỉ cho phép Patient truy cập vào /patient/**
                        .requestMatchers("/home").hasAnyRole("ADMIN", "DOCTOR", "PATIENT") // Cho phép Admin, Doctor, Patient truy cập vào /home
                        .anyRequest().authenticated()  // Các request khác cần phải xác thực
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Thêm filter JWT trước filter UsernamePasswordAuthenticationFilter

        return http.build();
    }
}

