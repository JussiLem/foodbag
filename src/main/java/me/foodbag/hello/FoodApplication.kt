package me.foodbag.hello


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class FoodApplication
    fun main(args: Array<String>) {
        SpringApplication.run(FoodApplication::class.java, *args)
    }
