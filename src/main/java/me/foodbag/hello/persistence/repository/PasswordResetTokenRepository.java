package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.PasswordResetToken;
import me.foodbag.hello.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  PasswordResetToken findByToken(String token);

  PasswordResetToken findByUser(User user);
}
