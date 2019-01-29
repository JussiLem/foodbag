package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long roleId;

  @ManyToMany(mappedBy = "role")
  private Collection<User> users;

  @ManyToMany
  @JoinTable(
      name = "rolesPrivileges",
      joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "privilegeId"),
      inverseJoinColumns = @JoinColumn(name = "privilegeId", referencedColumnName = "roleId"))
  private Collection<Privilege> privileges;

  private String name;
}
