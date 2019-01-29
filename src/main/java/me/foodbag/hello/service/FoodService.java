package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.Food;

import java.util.List;

public interface FoodService {

  Food getFriendById(Long id);

  Food getFriendByName(String name);

  List<Food> getAllFriends();

  boolean exists(String name);

  Food save(Food food);
}
