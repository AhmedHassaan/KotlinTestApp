package com.yelloco.kotlin_test_app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String
)
