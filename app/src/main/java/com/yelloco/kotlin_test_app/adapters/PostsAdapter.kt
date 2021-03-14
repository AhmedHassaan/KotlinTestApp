package com.yelloco.kotlin_test_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yelloco.kotlin_test_app.R
import com.yelloco.kotlin_test_app.callbacks.diifs.PostsDiffCallback
import com.yelloco.kotlin_test_app.retrofit.models.PostModel
import com.yelloco.kotlin_test_app.retrofit.models.UserModel


class PostsAdapter : ListAdapter<PostModel, PostsAdapter.PostViewHolder>(PostsDiffCallback()) {

    var postModels: MutableList<PostModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var userModels: MutableList<UserModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_posts, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        postModels.get(position).let {
            holder.bodyTextView.text = it.body
            holder.titleTextView.text = it.title
            holder.userTextView.text = getUserName(it.userId)
        }
    }

    override fun getItemCount(): Int {
        return postModels.size
    }

    private fun getUserName(userId: Int): String
    {
        for(userModel: UserModel in userModels)
        {
            if(userModel.id == userId)
            {
                return userModel.name
            }
        }

        return "Unknown"
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView =
            itemView.findViewById(R.id.list_item_posts_header_text_view)
        val bodyTextView: TextView =
            itemView.findViewById(R.id.list_item_posts_body_text_view)
        val userTextView: TextView =
            itemView.findViewById(R.id.list_item_posts_user_text_view)
    }
}