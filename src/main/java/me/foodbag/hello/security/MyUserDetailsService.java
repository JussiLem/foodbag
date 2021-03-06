package me.foodbag.hello.security;

import me.foodbag.hello.persistence.model.Privilege;
import me.foodbag.hello.persistence.model.Role;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.repository.UserRepository;
import me.foodbag.hello.web.exception.FoodBagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Retrieves the user from db and maps the right set of authorities from the roles and privileges the user
 * has assigned
 */
@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private HttpServletRequest httpServletRequest;

  @Autowired private LoginAttemptService loginAttemptService;

  public MyUserDetailsService() {
    super();
  }

  /**
   * Loads a user entity based on the username. Only allows the users to authenticate that are
   * enabled.
   * @param s name of the wanted user
   * @return details of the user
   * @throws UsernameNotFoundException if username not found
   */
  @Override
  public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
    final String ip = getClientIp();
    if (loginAttemptService.isBlocked(ip)) {
      throw new FoodBagException("blocked");
    }

    try {
      final User user = userRepository.findByEmail(s);
      if (user == null) {
        throw new UsernameNotFoundException("There was no user found with username" + s);
      }
      return new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          user.isEnabled(),
          true,
          true,
          true,
          getAuthorities(user.getRoles()));

    } catch (RuntimeException e) {
      throw new FoodBagException(e);
    }
  }

  /* UTILS for custom user detail service*/

  /**
   * Kun käyttäjä on autentikoinut, getAuthorities() "kansoitetaan" oliona
   *
   * @param roles
   * @return UserDetails palautetaan
   */
  private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<String> getPrivileges(final Collection<Role> roles) {
    final List<String> privileges = new ArrayList<>();
    final List<Privilege> collection = new ArrayList<>();

    for (final Role role : roles) {
      collection.addAll(role.getPrivileges());
    }
    for (final Privilege item : collection) {
      privileges.add(item.getName());
    }
    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
    final List<GrantedAuthority> authorities = new ArrayList<>();

    for (final String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }

  private String getClientIp() {
    final String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return httpServletRequest.getRemoteAddr();
    }
    return xfHeader.split(".")[0];
  }
}
