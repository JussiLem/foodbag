package me.foodbag.hello.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.foodbag.hello.captcha.ICaptchaService;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.registration.RegistrationEvent;
import me.foodbag.hello.service.UserService;
import me.foodbag.hello.web.dto.UserDto;
import me.foodbag.hello.web.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
public class RegistrationCaptchaController {

    @Autowired private UserService userService;

    @Autowired private ICaptchaService captchaService;

    @Autowired private ApplicationEventPublisher eventPublisher;

    public RegistrationCaptchaController() {
        super();
    }

    /* Registration */
    @PostMapping(value = "/user/registrationCaptcha")
    public GenericResponse captchaRegisterUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {

        final String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(response);
        log.debug("Registering user account with information: {}", accountDto);

        final User registered = userService.registerNewUserAccount(accountDto);

        eventPublisher.publishEvent(new RegistrationEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
