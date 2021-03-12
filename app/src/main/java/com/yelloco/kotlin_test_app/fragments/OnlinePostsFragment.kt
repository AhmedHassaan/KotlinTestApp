package com.yelloco.kotlin_test_app.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val classTag = this.javaClass.simpleName

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
    }

    private fun setListenerForViews()
    {
        binding.fragmentOnlinePostsCancelButton.setOnClickListener {
            GuiManager.removeSubFragment()
        }
    }

    private fun getAllPosts()
    {

        val postsCall: Call<List<PostModel>> = RetrofitClient.getAllPosts()
        postsCall.enqueue(object : Callback<List<PostModel>> {
            override fun onResponse(
                call: Call<List<PostModel>>,
                response: Response<List<PostModel>>
            ) {
                postsAdapter.postModels = response.body()!!
                fetchingPostsFinished = true
                fillDate()
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                t.stackTrace
            }

        })

        val usersCall: Call<List<UserModel>> = RetrofitClient.getAllUsers()
        usersCall.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                postsAdapter.userModels = response.body()!!
                fetchingUsersFinished = true
                fillDate()
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
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