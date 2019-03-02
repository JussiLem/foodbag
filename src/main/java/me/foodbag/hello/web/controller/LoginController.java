package me.foodbag.hello.web.controller;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.service.UserService;
import me.foodbag.hello.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestController
public class LoginController {



  @Autowired private UserService userService;

  @PostMapping(value = "/login")
  public String login(
          final Locale locale,
          final HttpServletRequest request,
          final @ModelAttribute UserDto userDto,
          BindingResult result) {
      try{
          request.login(userDto.getEmail(), userDto.getPassword());
          return "redirect:/login?lang=" + locale.getLanguage();
      } catch (ServletException e) {
          result.rejectValue(null, "authentication.failed");
          return "login";
      }
    }

    @GetMapping(value = "/login")
    public String login(@ModelAttribute UserDto userDto,
                        final Locale locale) {
        return "redirect:/login?lang=" + locale.getLanguage();
    }
  }

