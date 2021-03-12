package com.yelloco.kotlin_test_app.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yelloco.kotlin_test_app.adapters.PostsAdapter
import com.yelloco.kotlin_test_app.app_utils.GuiManager
import com.yelloco.kotlin_test_app.databinding.FragmentOnlinePostsBinding
import com.yelloco.kotlin_test_app.retrofit.RetrofitClient
import com.yelloco.kotlin_test_app.retrofit.models.PostModel
import com.yelloco.kotlin_test_app.retrofit.models.UserModel
import com.yelloco.kotlin_test_app.views.CustomProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnlinePostsFragment : Fragment() {

    private var _binding: FragmentOnlinePostsBinding? = null

    private val binding get() = _binding!!

    private lateinit var activity: Activity
    private lateinit var customProgressDialog: CustomProgressDialog
    private lateinit var postsAdapter: PostsAdapter
    private var fetchingPostsFinished: Boolean = false
    private var fetchingUsersFinished: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        activity = getActivity()!!
        _binding = FragmentOnlinePostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setListenerForViews()
        customProgressDialog = CustomProgressDialog(activity, binding.root)
        customProgressDialog.show()

        getAllPosts()
    }

    private fun initRecyclerView()
    {
        binding.fragmentOnlinePostsRecyclerView.layoutManager = LinearLayoutManager(activity)
        postsAdapter = PostsAdapter()
        binding.fragmentOnlinePostsRecyclerView.adapter = postsAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                postsAdapter.postModels.removeAt(position)
                postsAdapter.notifyItemRemoved(position)
            }
        }).attachToRecyclerView(binding.fragmentOnlinePostsRecyclerView)
    }

    private fun setListenerForViews()
    {
        binding.fragmentOnlinePostsCancelButton.setOnClickListener {
            GuiManager.removeSubFragment()
        }
    }

    private fun getAllPosts()
    {

        val postsCall: Call<MutableList<PostModel>> = RetrofitClient.getAllPosts()
        postsCall.enqueue(object : Callback<MutableList<PostModel>> {
            override fun onResponse(
                call: Call<MutableList<PostModel>>,
                response: Response<MutableList<PostModel>>
            ) {
                postsAdapter.postModels = response.body()!!
                fetchingPostsFinished = true
                fillDate()
            }

            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                t.stackTrace
            }

        })

        val usersCall: Call<MutableList<UserModel>> = RetrofitClient.getAllUsers()
        usersCall.enqueue(object : Callback<MutableList<UserModel>> {
            override fun onResponse(
                call: Call<MutableList<UserModel>>,
                response: Response<MutableList<UserModel>>
            ) {
                postsAdapter.userModels = response.body()!!
                fetchingUsersFinished = true
                fillDate()
            }

            override fun onFailure(call: Call<MutableList<UserModel>>, t: Throwable) {
                t.stackTrace
            }

        })
    }

    fun fillDate()
    {
        if(fetchingPostsFinished && fetchingUsersFinished)
        {
            customProgressDialog.dismiss()
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}