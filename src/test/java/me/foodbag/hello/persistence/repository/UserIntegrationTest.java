package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.persistence.model.VerificationToken;
import me.foodbag.hello.web.exception.FoodBagException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class UserIntegrationTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Apumuuttujat
    private Long tokenId;
    private Long userId;
    private static final String mailAddress = "test@example.com";
    private static final String testPassword = "SecretPass";
    private static final String firstName = "Teppo";
    private static final String lastName = "Testaaja";

    /**
     * Ennen testiä luodaan tietokantaan käyttäjä
     * @throws FoodBagException jos tapahtuu poikkeuksia
     */
    @BeforeEach
    public void givenUserAndVerificationToken() throws FoodBagException {
        User user = new User();
        user.setMailAddress(mailAddress);
        user.setPassword(testPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        entityManager.persist(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);

        entityManager.persist(verificationToken);

        entityManager.flush();
        entityManager.clear();

        tokenId = verificationToken.getId();
        userId = user.getId();
    }

    @Test
    public void whenContextIsLoadedThenCorrect() {
        Assert.assertTrue(userRepository.count() > 0);
        Assert.assertTrue(verificationTokenRepository.count() > 0);
    }

    @Test
    public void whenTokenIsRemovedThenUser() {
        verificationTokenRepository.deleteById(tokenId);
        userRepository.deleteById(userId);
    }
}
