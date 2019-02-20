package me.foodbag.hello.persistence.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserUnitTest {
  @Test
  public void whenCalledGetName_thenCorrect() {
    User user = new User();
    user.setFirstName("Bobby");
    user.setLastName("Boblander");
    user.setPassword("bobiverse");
    user.setEmail("bobs@domain.com");

    assertThat(user.getFirstName()).isEqualTo("Bobby");
  }

  @Test
  public void whenCalledGetEmail_thenCorrect() {
    User user = new User();
    user.setFirstName("Bobby");
    user.setLastName("Boblander");
    user.setPassword("bobiverse");
    user.setEmail("bobs@domain.com");

    assertThat(user.getEmail()).isEqualTo("bobs@domain.com");
  }

  @Test
  public void whenCalledSetName_thenCorrect() {
    User user = new User();
    user.setFirstName("Bobby");
    user.setLastName("Boblander");
    user.setPassword("bobiverse");
    user.setEmail("bobs@domain.com");

    user.setFirstName("John");
    assertThat(user.getFirstName()).isEqualTo("John");
  }

  @Test
  public void whenCalledSetEmail_thenCorrect() {
    User user = new User();
    user.setFirstName("Bobby");
    user.setLastName("Boblander");
    user.setPassword("bobiverse");
    user.setEmail("bobs@domain.com");

    user.setEmail("john@domain.com");
    assertThat(user.getEmail()).isEqualTo("john@domain.com");
  }

  @Test
  public void whenCalledtoString_thenCorrect() {
    User user = new User();
    user.setFirstName("Bobby");
    user.setLastName("Boblander");
    user.setPassword("bobiverse");
    user.setSecret("test");
    user.setEmail("bobs@domain.com");

    assertThat(user.toString())
        .isEqualTo(
            "User [id=0, firstName=Bobby, lastName=Boblander, email=bobs@domain.com, password=bobiverse, enabled=false, secret=test, roles=null]");
  }
}
