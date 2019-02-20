package me.foodbag.hello.persistence.model;

import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.*;
import java.util.Collection;

/** User taulun tietue / sisältää peruskäyttäjät ja pääkäyttäjien tiedot */
@Data
@Entity
@Table(name = "user_account")
public class User {
  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String firstName;

  private String lastName;

  private String email;

  @Column(length = 60)
  private String password;

  private boolean enabled;

  private String secret;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public User() {
    super();
    this.secret = Base32.random();
    this.enabled = false;
  }

  @Override
  public String toString() {
    return "User [id="
        + id
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + ", email="
        + email
        + ", password="
        + password
        + ", enabled="
        + enabled
        + ", secret="
        + secret
        + ", roles="
        + roles
        + "]";
  }
}
