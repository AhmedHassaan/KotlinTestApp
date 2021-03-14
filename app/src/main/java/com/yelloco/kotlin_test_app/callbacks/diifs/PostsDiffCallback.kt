package com.yelloco.kotlin_test_app.callbacks.diifs

import androidx.recyclerview.widget.DiffUtil
import com.yelloco.kotlin_test_app.retrofit.models.PostModel

class PostsDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return (oldItem.title.equals(newItem.title, true)) &&
                (oldItem.body.equals(newItem.body, true)) &&
                (oldItem.id == newItem.id) &&
                (oldItem.userId == newItem.id)
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}