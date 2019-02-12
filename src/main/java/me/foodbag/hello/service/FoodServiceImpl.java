package me.foodbag.hello.service;

import me.foodbag.hello.persistence.model.Food;
import me.foodbag.hello.persistence.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

  @Autowired private FoodRepository foodRepository;

  @Override
  public Food getFriendById(Long id) {
    return foodRepository.findById(id).orElse(null);
  }

  @Override
  public Food getFriendByName(String name) {
    return foodRepository.findByName(name);
  }

  @Override
  public List<Food> getAllFriends() {
    return foodRepository.findAll();
  }

  @Override
  public boolean exists(String name) {
    return foodRepository.findByName(name) != null;
  }

  @Override
  public Food save(Food food) {
    return foodRepository.save(food);
  }
}
