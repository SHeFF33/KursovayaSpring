package com.sheff.kursovaya.controllers;

import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.services.EmailService;
import com.sheff.kursovaya.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", userService.getUserByPrincipal(principal));
        } else {
            model.addAttribute("user", new User());
        }
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute @Valid User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля формы");
            return "registration";
        }

        boolean created = userService.createUser(user);
        if (!created) {
            model.addAttribute("errorMessage", "Пользователь с таким email уже существует!");
            return "registration";
        }
        model.addAttribute("message", "На вашу почту отправлено письмо для подтверждения регистрации.");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        boolean activated = userService.activateUser(code);
        if (activated) {
            model.addAttribute("message", "Ваш аккаунт успешно активирован!");
        } else {
            model.addAttribute("message", "Код активации недействителен!");
        }
        model.addAttribute("user", new User());
        return "login";
    }

}
