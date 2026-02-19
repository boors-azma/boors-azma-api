package com.ernoxin.boorsazmaapi.config;

import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.model.UserRole;
import com.ernoxin.boorsazmaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminBootstrapRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.email:}")
    private String email;

    @Value("${app.bootstrap.admin.phone-number:}")
    private String phoneNumber;

    @Value("${app.bootstrap.admin.national-code:}")
    private String nationalCode;

    @Value("${app.bootstrap.admin.first-name:}")
    private String firstName;

    @Value("${app.bootstrap.admin.last-name:}")
    private String lastName;

    @Value("${app.bootstrap.admin.password:}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {
        if (password == null || password.isBlank()) {
            return;
        }
        if (userRepository.existsByRole(UserRole.ADMIN)) {
            return;
        }
        if (isBlank(email) || isBlank(phoneNumber) || isBlank(nationalCode) || isBlank(firstName) || isBlank(lastName)) {
            log.warn("Admin bootstrap skipped: required properties are missing.");
            return;
        }
        if (password.length() < 8 || password.length() > 24) {
            log.warn("Admin bootstrap skipped: password must be between 8 and 24 characters.");
            return;
        }

        User admin = new User();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setNationalCode(nationalCode);
        admin.setPhoneNumber(phoneNumber);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(UserRole.ADMIN);
        userRepository.save(admin);
        log.info("Admin bootstrap user created: {}", email);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
