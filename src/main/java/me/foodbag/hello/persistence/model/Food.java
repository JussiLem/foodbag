package me.foodbag.hello.persistence.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  private String name;

  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private User user;

  public Food() {

    // hibernate
  }
  /** Constructor for the mock tests. If changed then mocks will fail. */
  public Food(long id, String name) {
    this.id = id;
    this.name = name;
  }
}
