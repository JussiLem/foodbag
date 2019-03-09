package me.foodbag.hello.persistence.model;

import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.*;
import java.util.Collection;

/**
 * User taulun tietue / sisältää peruskäyttäjät ja pääkäyttäjien tiedot For storing users, will
 * create a User entity that is mapped to a database table, with the following attributes:
 */
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

  /**
   * 60 characters is the length of the current crypted password
   */
  @Column(length = 60)
  private String password;

  /**
   * When the User is registered, this enabled field will be set to <i>false</i>.
   * During the account verification process - if successful it will become <i>true</i>
   * //Currently have to be enabled straight from the User table when new user registers
   */
  private boolean enabled;

  /**
   * Save a random secret code for each user to be used later in generating verification code
   */
  private String secret;
  /**
   * Represents the roles of the user in the system; each role will have a set of
   * low-level privileges. User-Role relationship is a flexible many to many.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  @OneToMany
  private Collection<Food> foods;

  public User() {
    super();
    this.secret = Base32.random();
    this.enabled = false;
  }

  /**
   * Editing this will break unit tests
   * @return User as a string
   */
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
        +", foods="
        + foods
        + "]";
  }
}
