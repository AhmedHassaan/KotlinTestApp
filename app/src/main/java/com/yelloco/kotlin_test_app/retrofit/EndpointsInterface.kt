package com.yelloco.kotlin_test_app.retrofit

import com.yelloco.kotlin_test_app.retrofit.models.PostModel
import com.yelloco.kotlin_test_app.retrofit.models.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface EndpointsInterface {

    @GET("posts")
    fun getAllPost(): Call<MutableList<PostModel>>

    @GET("users")
    fun getAllUsers(): Call<MutableList<UserModel>>
}