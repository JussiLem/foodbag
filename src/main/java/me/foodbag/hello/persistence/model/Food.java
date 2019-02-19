package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
@Table(name = "food")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  private LocalDateTime date;

  @Column(name = "price", columnDefinition = "DOUBLE")
  private Currency price;

  /**
   * Constructor for the mock tests. If changed then mocks will fail.
   * @param id food id
   * @param name name of the food
   */
  public Food(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Food() {
    //for hibernate
  }
}
