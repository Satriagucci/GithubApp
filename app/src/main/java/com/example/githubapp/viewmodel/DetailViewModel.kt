package com.example.githubapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.database.Favorite
import com.example.githubapp.database.FavoriteDao
import com.example.githubapp.database.FavoriteRoomDatabase
import com.example.githubapp.model.DetailResponse
import com.example.githubapp.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _userDetail = MutableLiveData<DetailResponse>()
    private val userDetail: LiveData<DetailResponse> = _userDetail

    private var userDao: FavoriteDao?
    private var userDb: FavoriteRoomDatabase?

    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String): LiveData<DetailResponse> {
        val client = ApiConfig.getApiService().getUsersDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("Failure", t.message!!)
            }
        })

        return userDetail
    }

    fun insertFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = Favorite(username, id, avatarUrl)
            userDao?.insert(user)
        }
    }

    suspend fun checkFavorite(id: Int) = userDao?.checkFavorite(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.delete(id)
        }
    }



}