package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.Food;

import java.util.List;

public interface FoodService {

  Food getFoodById(Long id);

  Food getFoodByName(String name);

  List<Food> getAllFoods();

  boolean exists(String name);

  Food save(Food food);
}
