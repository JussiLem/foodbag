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

  @Column(name = "userId")
  private Long userId;

  public Food(long foodId, String name) {
    this.id = foodId;
    this.name = name;
  }
}
