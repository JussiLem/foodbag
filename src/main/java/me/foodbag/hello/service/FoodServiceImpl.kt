package me.foodbag.hello.service

import me.foodbag.hello.persistence.model.Food
import me.foodbag.hello.persistence.repository.FoodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class FoodServiceImpl : FoodService {

    @Autowired
    private val foodRepository: FoodRepository? = null

    override val allFoods: List<Food>
        get() = foodRepository!!.findAll()

    override fun getFoodById(id: Long?): Food {
        return foodRepository!!.findById(id!!).orElse(null)
    }

    override fun getFoodByName(name: String): Food {
        return foodRepository!!.findByName(name)
    }

    override fun exists(name: String): Boolean {
        return foodRepository!!.findByName(name) != null
    }

    override fun save(food: Food): Food {
        return foodRepository!!.save(food)
    }
}
