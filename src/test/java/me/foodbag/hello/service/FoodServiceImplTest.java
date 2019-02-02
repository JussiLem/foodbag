package me.foodbag.hello.service;

import com.google.common.collect.Lists;
import me.foodbag.hello.persistence.model.Food;
import me.foodbag.hello.persistence.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
class FoodServiceImplTest {

  @TestConfiguration
  static class FoodServiceImplTestContextConfiguration {
    @Bean
    public FoodService employeeService() {
      return new FoodServiceImpl() {};
    }
  }

  @MockBean
  FoodRepository foodRepository = Mockito.mock(FoodRepository.class);


  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

  }

  @Test
  public void friendShouldBeFound()  {
    final AtomicLong counter = new AtomicLong();
    Food rice = new Food(counter.incrementAndGet(), "rice");
    Food spagetti = new Food(counter.incrementAndGet(), "spagetti");
    Food salmon = new Food(counter.incrementAndGet(), "salmon");

    List<Food> allFoods = Lists.newArrayList(rice, spagetti, salmon);
    Mockito.when(foodRepository.findByName(rice.getName())).thenReturn(rice);
    Mockito.when(foodRepository.findByName(spagetti.getName())).thenReturn(spagetti);
    Mockito.when(foodRepository.findByName("wrong name")).thenReturn(null);
    Mockito.when(foodRepository.findById(rice.getId())).thenReturn(Optional.of(rice));
    Mockito.when(foodRepository.findAll()).thenReturn(allFoods);
    Mockito.when(foodRepository.findById(-99L)).thenReturn(Optional.empty());


  }
}
