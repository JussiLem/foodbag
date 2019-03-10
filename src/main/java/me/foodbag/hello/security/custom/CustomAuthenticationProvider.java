package me.foodbag.hello.security.custom;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.repository.UserRepository;
import me.foodbag.hello.web.exception.FoodBagException;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

//@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

  @Autowired private UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final User user = userRepository.findByEmail(authentication.getName());
    if (user == null) {
      throw new FoodBagException("Invalid username or password");
    }

    // to verify verification code
    if (user.isUsing2FA()) {
      final String verificationCode = ((CustomWebAuthenticationDetails) authentication.getDetails()).getVerificationCode();
      final Totp totp = new Totp(user.getSecret());
      if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
        throw new BadCredentialsException("Invalid verfication code");
      }

    }
    final Authentication result = super.authenticate(authentication);
    return new UsernamePasswordAuthenticationToken(
        user, result.getCredentials(), result.getAuthorities());
  }

  private boolean isValidLong(String code) {
    try {
      Long.parseLong(code);
    } catch (final NumberFormatException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
