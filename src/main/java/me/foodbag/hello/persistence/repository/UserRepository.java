package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByMailAddress(String email);

  @Override
  void delete(User user);

  User findByUsername(String username);
}
