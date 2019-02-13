package me.foodbag.hello.spring;

import me.foodbag.hello.security.ActiveUserStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ActiveUserStorage activeUserStorage() {
        return new ActiveUserStorage();
    }
}
