package me.foodbag.hello.web.controller;

import me.foodbag.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/loggedUser")
    public String getLoggedUser(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

}