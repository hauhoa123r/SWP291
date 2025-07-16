package org.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.project.repository")  // Đảm bảo chỉ định package chứa UserRepository
public class JpaConfig {
    // Các cấu hình khác
}

