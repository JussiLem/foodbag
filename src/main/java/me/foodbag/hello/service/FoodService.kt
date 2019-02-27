package me.foodbag.hello.service

import me.foodbag.hello.persistence.model.Food

interface FoodService {

    val allFoods: List<Food>

    fun getFoodById(id: Long?): Food

    fun getFoodByName(name: String): Food

    fun exists(name: String): Boolean

    fun save(food: Food): Food
}
