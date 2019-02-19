package me.foodbag.hello.spring;

import com.google.common.collect.ImmutableList;
import me.foodbag.hello.persistence.model.Privilege;
import me.foodbag.hello.persistence.model.Role;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.repository.PrivilegeRepository;
import me.foodbag.hello.persistence.repository.RoleRepository;
import me.foodbag.hello.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/** Loads default settings for Foodbag */
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;
  private static final String READ_PRIVILEGE = "READ_PRIVILEGE";
  private static final String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
  private static final String CHANGE_PASSWORD_PRIVILEGE = "CHANGE_PASSWORD_PRIVILEGE";
  private static final String ROLE_USER = "ROLE_USER";
  private static final String ROLE_ADMIN = "ROLE_ADMIN";

  // Details for the test user
  private static final String TEST_EMAIL = "test@test.com";
  private static final String FIRST_NAME = "Test";
  private static final String LAST_NAME = "Test";
  private static final String PASS = "test";

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PrivilegeRepository privilegeRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  /*API */

  /** When application is started, we'll create privileges, roles and one test user */
  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (alreadySetup) {
      return;
    }

    /* Create initial privileges */
    final Privilege readPrivilege = createPrivilegeIfNotFound(READ_PRIVILEGE);
    final Privilege writePrivilege = createPrivilegeIfNotFound(WRITE_PRIVILEGE);
    final Privilege passwordPrivilege = createPrivilegeIfNotFound(CHANGE_PASSWORD_PRIVILEGE);

    /* Create initial roles */
    final List<Privilege> adminPrivileges =
        new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
    final List<Privilege> userPrivileges =
        new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
    final Role adminRole = createRoleIfNotFound(ROLE_ADMIN, adminPrivileges);
    createRoleIfNotFound(ROLE_USER, userPrivileges);

    /* Create initial user */
    createUserIfNotFound(
        TEST_EMAIL, FIRST_NAME, LAST_NAME, PASS, new ArrayList<>(ImmutableList.of(adminRole)));
    alreadySetup = true;
  }

  @Transactional
  public Privilege createPrivilegeIfNotFound(final String name) {
    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
      privilege = new Privilege(name);
      privilege = privilegeRepository.save(privilege);
    }
    return privilege;
  }

  @Transactional
  public Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role(name);
    }
    role.setPrivileges(privileges);
    role = roleRepository.save(role);
    return role;
  }

  @Transactional
  public User createUserIfNotFound(
      final String email,
      final String firstName,
      final String lastName,
      final String password,
      final Collection<Role> roles) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      user = new User();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setPassword(passwordEncoder.encode(password));
      user.setEmail(email);
      user.setEnabled(true);
    }
    user.setRoles(roles);
    user = userRepository.save(user);
    return user;
  }
}
