package com.sheff.kursovaya.utils;

import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.models.enums.Role;
import com.sheff.kursovaya.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPhoneNumber("+7(977)777-77-77");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setActive(true);
            admin.getRoles().add(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}

