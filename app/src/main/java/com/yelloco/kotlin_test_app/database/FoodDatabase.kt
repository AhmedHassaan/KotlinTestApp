package com.yelloco.kotlin_test_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yelloco.kotlin_test_app.database.models.Food

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    companion object {
        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun get(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "FoodDatabase"
                ).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    abstract fun foodDao(): FoodDao
}