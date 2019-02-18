package me.foodbag.hello.web.controller;

import me.foodbag.hello.security.ActiveUserStorage;
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

    @Autowired
    private ActiveUserStorage activeUserStorage;


    @GetMapping(value = "/loggedUser")
    public String getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStorage.getUsers());
        return "users";
    }

    @GetMapping(value = "/loggedUsersFromSessionRegistry")
    public String getLoggedUsersFromRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }

}
