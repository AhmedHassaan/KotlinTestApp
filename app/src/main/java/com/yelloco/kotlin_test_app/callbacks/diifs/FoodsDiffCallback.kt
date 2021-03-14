package com.yelloco.kotlin_test_app.callbacks.diifs

import androidx.recyclerview.widget.DiffUtil
import com.yelloco.kotlin_test_app.database.models.Food

class FoodsDiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name.equals(newItem.name, true)
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name.equals(newItem.name, true)
    }
}