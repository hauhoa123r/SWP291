package org.project.admin.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.project.admin.repository")
@EntityScan(basePackages = "org.project.admin.entity")  // Đảm bảo Spring quét đúng package chứa các entity
public class AdminJpaConfig {
}
