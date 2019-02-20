package me.foodbag.hello.persistence.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany
  @JoinTable(
      name = "roles_privileges",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;

  public Role(final String name) {
    super();
    this.name = name;
  }

  public Role() {
    super();
  }

  @Override
  public String toString() {
    return "Role [name=" + name + "]" + "[id=" + id + "]";
  }
}
