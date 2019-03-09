package me.foodbag.hello.persistence.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
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
            "User [id=0, firstName=Bobby, lastName=Boblander, email=bobs@domain.com, password=bobiverse, enabled=false, secret=test, roles=null, foods=null]");
  }

  @Test
  public void testIfUserHasFood() {
    User user = new User();

    user.setFirstName("Mike");
    user.setLastName("Hoffmann");
    user.setEmail("mike@hoff.com");
    user.setFoods(createFoodsForUser());
    assertThat(user.getFoods()).isNotEmpty();
    assertThat(user.getFoods().containsAll(createFoodsForUser())).isTrue();

  }

  private Collection<Food> createFoodsForUser() {
    Food food = new Food();
    food.setId(1);
    food.setName("Burger");
    food.setComment("Nice");
    Food food2 = new Food();
    food2.setId(2);
    food2.setName("Fries");
    food2.setName("Awsome");
    return Collections.unmodifiableList(Arrays.asList(food, food2));
  }
}
