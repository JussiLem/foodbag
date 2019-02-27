package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.Food;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Long> {

  Food findByName(String name);

  @Override
  List<Food> findAll();

}
