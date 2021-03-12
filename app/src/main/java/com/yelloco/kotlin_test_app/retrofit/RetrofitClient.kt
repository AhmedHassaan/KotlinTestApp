package com.yelloco.kotlin_test_app.retrofit

import com.yelloco.kotlin_test_app.retrofit.models.PostModel
import com.yelloco.kotlin_test_app.retrofit.models.UserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val API: EndpointsInterface by lazy {
        retrofit.create(EndpointsInterface::class.java)
    }

    fun getAllPosts() : Call<MutableList<PostModel>>
    {
        return API.getAllPost()
    }

    fun getAllUsers() : Call<MutableList<UserModel>>
    {
        return API.getAllUsers()
    }

}