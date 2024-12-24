package com.sheff.kursovaya.services;

import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.models.enums.Role;
import com.sheff.kursovaya.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);
        userRepository.save(user);
        String activationLink = "http://localhost:8080/activate/" + activationCode;
        emailService.sendActivationEmail(email, "Подтверждение регистрации", activationLink);
        return true;
    }

    @Transactional
    public boolean activateUser(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
            } else {
                user.setActive(true);
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}
