package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.stereotype.Service;

import java.util.List;

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
  User registerNewUserAccount(User user) throws FoodBagException;

  String getUser(User name);

  List<String> getAllUsers();

  void deleteUser(User user);

  User findUserByMailAddress(String email);

  void changeUserPassword(User user, String password);

  boolean checkIfValidPassword(User user, String password);

  List<String> getUserFromSessionRegistry();
}
