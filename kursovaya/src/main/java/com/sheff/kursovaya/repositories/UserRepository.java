package com.sheff.kursovaya.repositories;

import com.sheff.kursovaya.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByActivationCode(String activationCode);
    User findByEmail(String email);
}
