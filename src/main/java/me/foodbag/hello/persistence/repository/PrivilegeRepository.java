package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  Privilege findByName(String name);

  @Override
  void delete(Privilege privilege);
}
