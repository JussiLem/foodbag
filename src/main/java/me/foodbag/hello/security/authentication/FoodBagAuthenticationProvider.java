package me.foodbag.hello.security.authentication;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

// @Component
public class FoodBagAuthenticationProvider extends DaoAuthenticationProvider {

  @Autowired private UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final User user = userRepository.findByEmail(authentication.getName());
    if (user == null) {
      throw new BadCredentialsException("Invalid username or password!");
    }
    final Authentication result = super.authenticate(authentication);
    return new UsernamePasswordAuthenticationToken(
        user, result.getCredentials(), result.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
