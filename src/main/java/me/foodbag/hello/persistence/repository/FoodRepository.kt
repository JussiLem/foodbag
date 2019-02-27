package me.foodbag.hello.persistence.repository

import me.foodbag.hello.persistence.model.Food
import org.springframework.data.repository.CrudRepository

interface FoodRepository : CrudRepository<Food, Long> {

    fun findByName(name: String): Food

    override fun findAll(): List<Food>

}
