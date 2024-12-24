package com.sheff.kursovaya.models;

import com.sheff.kursovaya.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true, updatable = false)
        @NotBlank(message = "Почта не может быть пустой")
        @Email(message = "Почта должна быть корректным email-адресом")
        private String email;
        @NotBlank(message = "Номер телефона не может быть пустым")
        @Size(max = 15, message = "Номер телефона не может содержать больше 15 символов")
        @Pattern(regexp = "^(\\+7|7|8)\\(?\\d{3}\\)?\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона должен быть в формате +7(XXX)XXX-XX-XX или 8(XXX)XXX-XX-XX")
        private String phoneNumber;
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 30, message = "Имя не может содержать больше 30 символов")
        private String name;
        private boolean active;
        private String activationCode;
        @Column(length = 1000)
        @NotBlank(message = "Пароль не может быть пустым")
        @Pattern(regexp = "^[A-Za-z0-9]{8,}$", message = "Пароль должен содержать минимум 8 символов и включать только цифры или латинские буквы")
        private String password;

        @NotBlank(message = "Поле не может быть пустым")
        @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
        @Enumerated(EnumType.STRING)
        private Set<Role> roles = new HashSet<>();

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
        private List<Product> products = new ArrayList<>();

        public boolean isAdmin() {
            return roles.contains(Role.ROLE_ADMIN);
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getActivationCode() {
            return activationCode;
        }

        public void setActivationCode(String activationCode) {
            this.activationCode = activationCode;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return active;
        }
    }


