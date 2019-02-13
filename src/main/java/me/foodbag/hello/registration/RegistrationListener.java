package me.foodbag.hello.registration;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import org.springframework.stereotype.Component;

import java.util.UUID;

/** Kuuntelee rekisteröitymisiä */
@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

  @Autowired private UserService userService;

  private void confirmUserRegistration(final RegistrationEvent completeEvent) {
    final User user = completeEvent.getUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
  }

  @Override
  public void onApplicationEvent(RegistrationEvent registrationEvent) {
    this.confirmUserRegistration(registrationEvent);
  }
}
