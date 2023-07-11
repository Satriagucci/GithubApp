package com.example.githubapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_table")
data class Favorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
): Serializable