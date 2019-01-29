package me.foodbag.hello.persistence.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Collection;

/** Admin taulun tietue / pääkäyttäjätunnus */
@Data
@Entity
@Table(name = "admin")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long adminId;

  @Column(name = "name", columnDefinition = "TEXT", nullable = false)
  private String name;

  @Column(name = "password", columnDefinition = "TEXT", nullable = false)
  private String password;

  private boolean enabled;

  @ManyToMany(
      targetEntity = Role.class,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "role",
      joinColumns = @JoinColumn(name = "adminId", referencedColumnName = "adminId"),
      inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "adminId"))
  private Collection<Role> role;
}
