package me.foodbag.hello.web.controller;

import me.foodbag.hello.security.ActiveUserStore;
import me.foodbag.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActiveUserStore activeUserStorage;


    @GetMapping(value = "/loggedUsers")
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
