package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.model.VerificationToken;
import me.foodbag.hello.web.dto.UserDto;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Peruskäyttäjille luotu service luokka, Vastuussa esim. tilin luomisesta ja tarvittavan logiikan
 * suorittamisesta käyttäjän rekisteröimiseksi.
 */
@Service
public interface UserService {

  /**
   * Rekisteröi järjestelmään uuden peruskäyttäjän
   *
   * @param user lisättävä käyttäjä
   * @return palauttaa uuden käyttäjän
   * @throws FoodBagException jos tulee poikkeuksia
   */
  User registerNewUserAccount(UserDto user) throws FoodBagException;

  User getUser(String verificationToken);

  void deleteUser(User user);

  User findUserByMailAddress(String email);

  void changeUserPassword(User user, String password);

  boolean checkIfValidOldPassword(User user, String password);

  VerificationToken getVerificationToken(String verificationToken);

  VerificationToken generateNewVerificationToken(String token);

  String validateVerificationToken(String token);

  void createPasswordResetTokenForUser(User user, String token);

  void createVerificationTokenForUser(User user, String token);

  String validatePasswordResetToken(long id, String token);

  Optional<User> getUserById(long id);

  List<String> getUsersFromSessionRegistry();
}
