package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  Admin findByName(String name);

  @Override
  void delete(Admin admin);
}
