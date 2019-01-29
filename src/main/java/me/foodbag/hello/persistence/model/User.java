package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

/** User taulun tietue / peruskäyttäjä */
@Data
@Entity
@Table(name = "user")
public class User {
  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 1)
  @Column(name = "first_name", columnDefinition = "TEXT", nullable = false)
  private String firstName;

  @NotNull
  @Size(min = 1)
  @Column(name = "last_name", columnDefinition = "TEXT", nullable = false)
  private String lastName;

  @NotNull
  @Size(min = 1)
  @Column(name = "user_account", columnDefinition = "TINYTEXT", nullable = false)
  private String userAccount;

  @ManyToMany
  @JoinTable(
      name = "user_usergroup",
      joinColumns = @JoinColumn(name = "id", referencedColumnName = "groupid"),
      inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "id"))
  private UserGroup userGroup;

  @Column(name = "gender", columnDefinition = "TEXT")
  private String gender;

  private LocalDateTime birthDay;

  @NotNull
  @Size(min = 1)
  @Column(name = "mailAddress", columnDefinition = "TEXT", nullable = false)
  private String mailAddress;

  @NotNull
  @Size(min = 1)
  @Column(name = "user_password", columnDefinition = "TEXT")
  private String password;

  private boolean enabled;

  private String secret;

  @ManyToMany(
      targetEntity = Role.class,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "roles",
      joinColumns = @JoinColumn(name = "id", referencedColumnName = "roleId"),
      inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
  private Collection<Role> roles;
}
