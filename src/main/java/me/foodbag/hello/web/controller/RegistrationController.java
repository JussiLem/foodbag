package me.foodbag.hello.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.foodbag.hello.persistence.model.Privilege;
import me.foodbag.hello.persistence.model.Role;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.model.VerificationToken;
import me.foodbag.hello.registration.RegistrationEvent;
import me.foodbag.hello.service.UserService;
import me.foodbag.hello.web.util.GenericResponse;
import me.foodbag.hello.web.dto.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class RegistrationController {

  @Autowired private UserService userService;

  @Autowired private MessageSource messages;

  @Autowired private ApplicationEventPublisher eventPublisher;

  // Registration

  @PostMapping(value = "/user/registration")
  public GenericResponse registerUserAccount(
      @Valid final User accountDto, final HttpServletRequest request) {
    log.debug("Registering user account with information: {}", accountDto);

    final User registered = userService.registerNewUserAccount(accountDto);
    eventPublisher.publishEvent(
        new RegistrationEvent(registered, request.getLocale(), getAppUrl(request)));
    return new GenericResponse("success");
  }

  @GetMapping(value = "/registrationConfirm")
  public String confirmRegistration(
      final HttpServletRequest request,
      final Model model,
      @RequestParam("token") final String token) {
    Locale locale = request.getLocale();
    final String result = userService.validateVerificationToken(token);
    if (result.equals("valid")) {
      final User user = userService.getUser(token);
      // if (user.isUsing2FA()) {
      // model.addAttribute("qr", userService.generateQRUrl(user));
      // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
      // }
      authWithoutPassword(user);
      model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
      return "redirect:/console.html?lang=" + locale.getLanguage();
    }

    model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
    model.addAttribute("expired", "expired".equals(result));
    model.addAttribute("token", token);
    return "redirect:/badUser.html?lang=" + locale.getLanguage();
  }

  // user activation - verification

  @GetMapping(value = "/user/resendRegistrationToken")
  public GenericResponse resendRegistrationToken(
      final HttpServletRequest request, @RequestParam("token") final String existingToken) {
    final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
    final User user = userService.getUser(newToken.getToken());
    // mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request),
    // request.getLocale(), newToken, user));
    return new GenericResponse(
        messages.getMessage("message.resendToken", null, request.getLocale()));
  }

  // Reset password
  @PostMapping(value = "/user/resetPassword")
  public GenericResponse resetPassword(
      final HttpServletRequest request, @RequestParam("email") final String userEmail) {
    final User user = userService.findUserByMailAddress(userEmail);
    if (user != null) {
      final String token = UUID.randomUUID().toString();
      userService.createPasswordResetTokenForUser(user, token);
      //    mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token,
      // user));
    }
    return new GenericResponse(
        messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
  }

  @GetMapping(value = "/user/changePassword")
  public String showChangePasswordPage(
      final Locale locale,
      final Model model,
      @RequestParam("id") final long id,
      @RequestParam("token") final String token) {
    final String result = userService.validatePasswordResetToken(id, token);
    if (result != null) {
      model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
      return "redirect:/login?lang=" + locale.getLanguage();
    }
    return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
  }

  @PostMapping(value = "/user/savePassword")
  public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDao) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    userService.changeUserPassword(user, passwordDao.getNewPassword());
    return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
  }
  /*
  // change user password
  @PostMapping(value = "/user/updatePassword")
  public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordValid passwordDto) {
      final User user = userService.findUserByMailAddress(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
      if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
          throw new InvalidOldPasswordException();
      }
      userService.changeUserPassword(user, passwordDto.getNewPassword());
      return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
  }

  */
  private String getAppUrl(HttpServletRequest request) {
    return "http://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + request.getContextPath();
  }

  private void authWithoutPassword(User user) {
    List<Privilege> privileges = new ArrayList<>();
    Set<Privilege> uniqueValues = new HashSet<>();
    for (Role role : user.getRoles()) {
      Collection<Privilege> rolePrivileges = role.getPrivileges();
      for (Privilege privilege : rolePrivileges) {
        if (uniqueValues.add(privilege)) {
          privileges.add(privilege);
        }
      }
    }
    List<GrantedAuthority> authorities =
        privileges.stream()
            .map(p -> new SimpleGrantedAuthority(p.getName()))
            .collect(Collectors.toList());

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(user, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
