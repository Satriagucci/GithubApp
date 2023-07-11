package com.example.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.adapter.UserAdapter
import com.example.githubapp.database.Favorite
import com.example.githubapp.databinding.ActivityFavoriteBinding
import com.example.githubapp.model.User
import com.example.githubapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this, {
            if (it != null) {
                val listFavorite = mapFavorite(it)
                adapter.setList(listFavorite)
            }
        })
    }

    private fun mapFavorite(favUser: List<Favorite>): ArrayList<User> {
        val list = ArrayList<User>()
        for (user in favUser) {
            val userMapping = User(
                user.login,
                user.avatar_url,
                user.id
            )
            list.add(userMapping)
        }
        return list
    }

}