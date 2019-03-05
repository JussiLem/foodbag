package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Privilege entity represents a low-level, granular privilege/authority in the FoodBag System
 */
@Data
@Entity
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;

  public Privilege(final String name) {
    super();
    this.name = name;
  }

  public Privilege() {
    // hibernate
  }
}
