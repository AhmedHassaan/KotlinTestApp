package com.yelloco.kotlin_test_app.database

import androidx.room.*
import com.yelloco.kotlin_test_app.database.models.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFood(food: Food)

    @Query("SELECT * FROM Food")
    fun getAll() : List<Food>

    @Query("SELECT EXISTS(SELECT * FROM Food WHERE name = :name)")
    fun isFoodExist(name : String) : Boolean

    @Delete
    fun deleteFood(food: Food)
}