package me.foodbag.hello.persistence.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoodUnitTest {

    @Test
    fun whenCalledGetCorrectFoodNameAndComment() {
        val food = Food()
        food.name = "Hamburger"
        food.comment = "Really Good"

        assertThat(food.name).isEqualTo("Hamburger")
        assertThat(food.comment).isEqualTo("Really Good")

    }

}