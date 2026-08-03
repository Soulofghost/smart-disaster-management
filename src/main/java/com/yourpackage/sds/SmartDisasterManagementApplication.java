package com.yourpackage.sds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.yourpackage.sds.repository.UserRepository;
import com.yourpackage.sds.repository.AdminRepository;
import com.yourpackage.sds.entity.User;
import com.yourpackage.sds.entity.AdminUser;
import com.yourpackage.sds.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableAutoConfiguration
public class SmartDisasterManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartDisasterManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, 
                                  AdminRepository adminRepository, 
                                  PasswordEncoder passwordEncoder,
                                  JdbcTemplate jdbcTemplate) {
        return args -> {
            if (!adminRepository.findByEmail("admin@ksdma.gov.in").isPresent()) {
                AdminUser admin = new AdminUser(
                    "State Disaster Management Admin",
                    "admin@ksdma.gov.in",
                    passwordEncoder.encode("admin123"),
                    "Kerala State Disaster Control Centre",
                    "0471-2331018"
                );
                adminRepository.save(admin);
                System.out.println("Default Admin Seeded into sds_admins table: admin@ksdma.gov.in / admin123");
            }
        };
    }
}
