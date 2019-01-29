package me.foodbag.hello.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.registration.RegistrationEvent;
import me.foodbag.hello.service.UserService;
import me.foodbag.hello.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /* API */
    // Registration

   @PostMapping(value = "/user/registration")
   public GenericResponse registerUserAccount(@Valid final User user, final HttpServletRequest request) {
       log.debug("Trying to register | {} | user account", user);

       final User registered = userService.registerNewUserAccount(user);
       eventPublisher.publishEvent(new RegistrationEvent(registered, request.getLocale(), getAppUrl(request)));
       return new GenericResponse("success");
   }

   private String getAppUrl(HttpServletRequest request) {
       return "http://" + request.getServerName() + ":" +request.getServerPort() + request.getContextPath();
   }
}
