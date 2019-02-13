package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;

/** User taulun tietue / peruskäyttäjä */
@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Size(min = 1)
  @Column(name = "first_name")
  private String firstName;

  @Size(min = 1)
  @Column(name = "last_name")
  private String lastName;

  @Size(min = 1)
  private String username;

  private String gender;

  private LocalDate birthDay;

  @Size(min = 1)
  @Column(name = "email_address")
  private String mailAddress;

  @Size(min = 1)
  @Column(name = "user_password")
  private String password;

  private boolean enabled;

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Collection<Role> role;

  public Collection<Role> getRoles() {
    return role;
  }
}
