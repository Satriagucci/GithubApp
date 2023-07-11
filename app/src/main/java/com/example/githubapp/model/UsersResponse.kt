package com.example.githubapp.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(

    @field:SerializedName("items")
    val items: ArrayList<User>
)