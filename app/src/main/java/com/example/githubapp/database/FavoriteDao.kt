package com.example.githubapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite_table")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_table WHERE favorite_table.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM favorite_table WHERE favorite_table.id = :id")
    suspend fun delete(id: Int): Int
}