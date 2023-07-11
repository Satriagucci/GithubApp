package com.example.githubapp.network

import com.example.githubapp.model.DetailResponse
import com.example.githubapp.model.User
import com.example.githubapp.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUsers(
        @Query("q") query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}