package me.foodbag.hello.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "me.foodbag.hello.service"})
public class ServiceConfig {
}
