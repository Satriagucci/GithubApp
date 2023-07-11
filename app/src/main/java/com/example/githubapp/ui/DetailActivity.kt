package com.example.githubapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubapp.R
import com.example.githubapp.adapter.SectionPagerAdapter
import com.example.githubapp.databinding.ActivityDetailBinding
import com.example.githubapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    private fun showLoading(isLoading: Boolean) =
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this
        )[DetailViewModel::class.java]

        showLoading(true)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        if (username != null) {
            viewModel.setUserDetail(username).observe(this) {
                if (it != null) {
                    binding.apply {
                        tvName.text = it.login
                        tvUsername.text = it.login
                        tvFollowers.text = "${it.followers} Followers"
                        tvFollowing.text = "${it.following} Following"
                        Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(ivProfile)

                    }
                }
                showLoading(false)
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.favoriteSwitch.isChecked =  true
                        _isChecked = true

                    }
                }
            }
        }

        binding.favoriteSwitch.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.insertFavorite(username, id, avatarUrl)
                    }
                }
            } else {
                viewModel.deleteFavorite(id)
            }
            binding.favoriteSwitch.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }
}
