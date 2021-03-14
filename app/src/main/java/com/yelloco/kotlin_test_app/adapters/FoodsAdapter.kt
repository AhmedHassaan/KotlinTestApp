package com.yelloco.kotlin_test_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yelloco.kotlin_test_app.R
import com.yelloco.kotlin_test_app.callbacks.diifs.FoodsDiffCallback
import com.yelloco.kotlin_test_app.database.models.Food

class FoodsAdapter : ListAdapter<Food, FoodsAdapter.FoodViewHolder>(FoodsDiffCallback()) {

    var foods: MutableList<Food> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_food, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        foods[position].let {
            val positionString = "${foods[position].id}."
            holder.idTextView.text = positionString
            holder.nameTextView.text = it.name
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView =
            itemView.findViewById(R.id.list_item_food_id_text_view)
        val nameTextView: TextView =
            itemView.findViewById(R.id.list_item_food_name_text_view)
    }

}