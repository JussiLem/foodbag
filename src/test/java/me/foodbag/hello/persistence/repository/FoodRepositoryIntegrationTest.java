package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.Food;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Testi skenaarioita {@link FoodRepository} varten.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest // Mahdollistaa standardi asetukset testien ajamista varten
public class FoodRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoodRepository foodRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

    @Test
    public void whenFindByName_thenReturnFriend() {
        // given
        Food rice = new Food(1, "Rice");
        entityManager.persist(rice);
        entityManager.flush();

        // when
        Food found = foodRepository.findByName(rice.getName());

        // then
        Assert.assertEquals(rice.getName(), found.toString());
    }

}