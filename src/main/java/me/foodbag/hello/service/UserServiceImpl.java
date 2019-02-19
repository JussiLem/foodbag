package me.foodbag.hello.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import me.foodbag.hello.persistence.model.PasswordResetToken;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.model.VerificationToken;
import me.foodbag.hello.persistence.repository.PasswordResetTokenRepository;
import me.foodbag.hello.persistence.repository.RoleRepository;
import me.foodbag.hello.persistence.repository.UserRepository;
import me.foodbag.hello.persistence.repository.VerificationTokenRepository;
import me.foodbag.hello.web.dto.UserDto;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/** Service class idea is to hide the real repository and have all the real functionality. */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private VerificationTokenRepository verificationTokenRepository;

  @Autowired private PasswordResetTokenRepository passwordTokenRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private SessionRegistry sessionRegistry;

  private static final String TOKEN_INVALID = "invalidToken";
  private static final String TOKEN_EXPIRED = "expired";
  private static final String TOKEN_VALID = "valid";
  private static final String ROLE_USER = "ROLE_USER";

  @Override
  public User registerNewUserAccount(final UserDto account) {
    if (emailExists(account.getEmail())) {
      throw new FoodBagException(
          "User already exists with that email address:" + account.getEmail());
    }
    final User user = new User();
    user.setFirstName(account.getFirstName());
    user.setLastName(account.getLastName());
    user.setPassword(passwordEncoder.encode(account.getPassword()));
    user.setEmail(account.getEmail());
    user.setRoles(ImmutableSet.of(roleRepository.findByName(ROLE_USER)));
    return userRepository.save(user);
  }

  @Override
  public User getUser(final String verificationToken) {
    final VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
    if (token != null) {
      return token.getUser();
    }
    return null;
  }

  @Override
  public void deleteUser(final User user) {
    userRepository.delete(user);
  }

  @Override
  public User findUserByMailAddress(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void changeUserPassword(User user, String oldPassword) {
    user.setPassword(oldPassword);
    userRepository.save(user);
  }

  @Override
  public boolean checkIfValidOldPassword(User user, String password) {
    return false;
  }

  @Override
  public VerificationToken getVerificationToken(String verificationToken) {
    return verificationTokenRepository.findByToken(verificationToken);
  }

  @Override
  public VerificationToken generateNewVerificationToken(String token) {
    VerificationToken vToken = verificationTokenRepository.findByToken(token);
    vToken.updateToken(UUID.randomUUID().toString());
    vToken = verificationTokenRepository.save(vToken);
    return vToken;
  }

  @Override
  public String validateVerificationToken(String token) {
    final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    if (verificationToken == null) {
      return TOKEN_INVALID;
    }

    final User user = verificationToken.getUser();
    final Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      verificationTokenRepository.delete(verificationToken);
      return TOKEN_EXPIRED;
    }

    user.setEnabled(true);
    userRepository.save(user);
    return TOKEN_VALID;
  }

  @Override
  public void createPasswordResetTokenForUser(User user, String token) {
    final PasswordResetToken myToken = new PasswordResetToken(token, user);
    passwordTokenRepository.save(myToken);
  }

  @Override
  public void createVerificationTokenForUser(final User user, final String token) {
    final VerificationToken token1 = new VerificationToken(token, user);
    verificationTokenRepository.save(token1);
  }

  @Override
  public String validatePasswordResetToken(long id, String token) {
    final PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
    if (passwordResetToken == null || passwordResetToken.getUser().getId() != id) {
      return TOKEN_INVALID;
    }
    final Calendar calendar = Calendar.getInstance();
    if (passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
      return TOKEN_EXPIRED;
    }

    final User user = passwordResetToken.getUser();
    final Authentication auth =
        new UsernamePasswordAuthenticationToken(
            user, null, ImmutableList.of(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
    SecurityContextHolder.getContext().setAuthentication(auth);
    return null;
  }

  @Override
  public Optional<User> getUserById(final long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<String> getUsersFromSessionRegistry() {
    return sessionRegistry.getAllPrincipals().stream()
        .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
        .map(
            o -> {
              if (o instanceof User) {
                return ((User) o).getEmail();
              } else {
                return o.toString();
              }
            })
        .collect(Collectors.toList());
  }

  private boolean emailExists(final String email) {
    return userRepository.findByEmail(email) != null;
  }
}
