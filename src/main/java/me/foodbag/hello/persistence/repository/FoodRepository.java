package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Long> {

  Food findByName(String name);

  @Override
  List<Food> findAll();

//  Collection<Food> find(String username);
}
