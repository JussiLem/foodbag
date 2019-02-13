package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.PasswordResetToken;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.model.VerificationToken;
import me.foodbag.hello.persistence.repository.PasswordResetTokenRepository;
import me.foodbag.hello.persistence.repository.RoleRepository;
import me.foodbag.hello.persistence.repository.TokenRepository;
import me.foodbag.hello.persistence.repository.UserRepository;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private TokenRepository tokenRepository;

  @Autowired private PasswordResetTokenRepository passwordTokenRepository;

  public static final String TOKEN_INVALID = "invalidToken";
  public static final String TOKEN_EXPIRED = "expired";
  public static final String TOKEN_VALID = "valid";

  private List<String> users;

  @Override
  @Transactional
  public User registerNewUserAccount(final User account) {
    if (emailExists(account.getMailAddress())) {
      throw new FoodBagException(
          "User already exists with that email address:" + account.getMailAddress());
    }
    final User user = new User();
    user.setFirstName(account.getFirstName());
    user.setLastName(account.getLastName());
    user.setUsername(account.getUsername());
    user.setPassword(account.getPassword());
    user.setBirthDay(account.getBirthDay());
    user.setMailAddress(account.getMailAddress());
    user.setGender(account.getGender());
    user.setRole(account.getRole());
    return userRepository.save(user);
  }

  @Override
  public User getUser(final String verificationToken) {
    final VerificationToken token = tokenRepository.findByToken(verificationToken);
    if (token != null) {
      return token.getUser();
    }
    return null;
  }

    @Override
    public List<String> getAllUsers() {
        return users;
    }


    @Override
  public void deleteUser(final User user) {
    userRepository.delete(user);
  }

  @Override
  public User findUserByMailAddress(String email) {
    return userRepository.findByMailAddress(email);
  }

  @Override
  public void changeUserPassword(User user, String oldPassword) {
    user.setPassword(oldPassword);
    userRepository.save(user);
  }

  @Override
  public boolean checkIfValidPassword(User user, String password) {
    return false;
  }

  @Override
  public List<String> getUserFromSessionRegistry() {
    return null;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public VerificationToken generateNewVerificationToken(String token) {
    VerificationToken vToken = tokenRepository.findByToken(token);
    vToken.updateToken(UUID.randomUUID()
            .toString());
    vToken = tokenRepository.save(vToken);
    return vToken;
  }

  @Override
  public String validateVerificationToken(String token) {
    final VerificationToken verificationToken = tokenRepository.findByToken(token);
    if (verificationToken == null) {
      return TOKEN_INVALID;
    }

    final User user = verificationToken.getUser();
    final Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate()
            .getTime()
            - cal.getTime()
            .getTime()) <= 0) {
      tokenRepository.delete(verificationToken);
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
    tokenRepository.save(token1);

  }

  private boolean emailExists(final String email) {
    return userRepository.findByMailAddress(email) != null;
  }


}
