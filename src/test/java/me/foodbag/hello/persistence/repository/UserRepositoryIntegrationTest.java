package me.foodbag.hello.persistence.repository;

import me.foodbag.hello.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired UserRepository userRepository;

    @Test
    public void whenCalledSave_thenCorrectNumberOfUsers() {
        User user = new User();
        user.setFirstName("Bob");
        user.setLastName("Bobland");
        user.setPassword("bobverse");
        user.setEmail("bob@domain.com");
        userRepository.save(user);
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
    }
}
