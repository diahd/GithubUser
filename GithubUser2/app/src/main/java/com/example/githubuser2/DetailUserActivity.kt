package com.example.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.githubuser2.data.Users
import com.example.githubuser2.databinding.ActivityDetailUserBinding
import com.example.githubuser2.tablayout.SectionsPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
        private val TAG = DetailUserActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        showData()
    }

    private fun showData() {
        val user = intent.getParcelableExtra<Users>(EXTRA_USER) as Users

        binding.nameReceived.text = user.name.toString()
        binding.userReceived.text = user.user.toString()
        binding.location.text = user.loc.toString()
        binding.company.text = user.company.toString()
        binding.repositories.text = user.repository.toString()
        binding.following.text = user.following.toString()
        binding.followers.text = user.followers.toString()
        Glide
            .with(this)
            .load(user.photo)
            .centerCrop()
            .into(binding.photoReceived)

        getTabLayout(user)
    }

    private fun getTabLayout(data: Users) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = data.user.toString()
        Log.d(TAG, data.user.toString())
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        supportActionBar?.elevation = 0f
    }

    //agar navigasi back berfungsi
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}