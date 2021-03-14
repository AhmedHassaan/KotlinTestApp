package com.yelloco.kotlin_test_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yelloco.kotlin_test_app.database.models.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFood(food: Food)

    @Query("SELECT * FROM Food")
    fun getAll() : List<Food>
}