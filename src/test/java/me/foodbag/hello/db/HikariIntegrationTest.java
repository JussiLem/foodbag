package me.foodbag.hello.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

/** Testataan, että spring boot käyttää varmasti Hikari DataSourcea */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.datasource.type=com.zaxxer.hikari.HikariDataSource")
public class HikariIntegrationTest {

  @Autowired private DataSource dataSource;

  @Test
  public void hikariConnectionPoolIsConfigured() {
    Assertions.assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource.getClass().getName());
  }
}
