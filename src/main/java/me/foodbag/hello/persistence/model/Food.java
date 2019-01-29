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
  private long foodId;

  @Column(name = "name", columnDefinition = "TEXT")
  private String name;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  private LocalDateTime date;

  @Column(name = "price", columnDefinition = "DOUBLE")
  private Currency price;

  public Food(long foodId, String name) {
    this.foodId = foodId;
    this.name = name;
  }
}
