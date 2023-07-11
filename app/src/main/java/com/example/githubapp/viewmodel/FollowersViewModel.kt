package com.example.githubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.model.User
import com.example.githubapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val lisFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        val client = ApiConfig.getApiService().getUsersFollowers(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    lisFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Failure", t.message!!)
            }

        })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return lisFollowers
    }
}