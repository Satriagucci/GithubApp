package com.example.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapp.database.Favorite
import com.example.githubapp.database.FavoriteDao
import com.example.githubapp.database.FavoriteRoomDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: FavoriteRoomDatabase?

    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<Favorite>>? {
        return userDao?.getFavorite()
    }

}