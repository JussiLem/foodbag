package me.foodbag.hello.spring;

import me.foodbag.hello.security.ActiveUserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ActiveUserStore activeUserStorage() {
        return new ActiveUserStore();
    }
}
