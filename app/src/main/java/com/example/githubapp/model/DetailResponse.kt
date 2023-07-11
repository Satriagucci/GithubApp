package com.example.githubapp.model

import com.squareup.moshi.Json

data class DetailResponse(
    @field:Json(name = "login")
    val login: String,

    @field:Json(name = "avatarUrl")
    val avatar_url: String,

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "followers")
    val followers: Int,

    @field:Json(name = "following_url")
    val followingUrl: String,

    @field:Json(name = "following")
    val following: Int,

    @field:Json(name = "followers_url")
    val followersUrl: String,
)
