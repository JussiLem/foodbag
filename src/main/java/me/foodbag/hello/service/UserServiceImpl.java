package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.repository.RoleRepository;
import me.foodbag.hello.persistence.repository.UserRepository;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

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
  public String getUser(final User user) {
    return user.getUsername();
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

  private boolean emailExists(final String email) {
    return userRepository.findByMailAddress(email) != null;
  }
}
