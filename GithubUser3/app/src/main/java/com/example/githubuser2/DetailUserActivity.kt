package com.example.githubuser2

import android.content.ContentValues
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.githubuser2.data.Users
import com.example.githubuser2.databinding.ActivityDetailUserBinding
import com.example.githubuser2.db.UserContract
import com.example.githubuser2.favorite.FavUsers
import com.example.githubuser2.tablayout.SectionsPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private var statusFav: Boolean = false
    private var fav: FavUsers? = null
    private lateinit var uri: Uri
    private lateinit var photo: String

    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAV = "extra_fav"

        private val TAG = DetailUserActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fav = intent.getParcelableExtra(EXTRA_FAV)

        if (fav != null){
            showDatafromFav()
        }else{
            showData()
        }

        val username = binding.userReceived.text.toString()
        uri = Uri.parse(UserContract.UserColumns.CONTENT_URI.toString() + "/" + username)

        val actionbar = supportActionBar
        actionbar!!.title = username
        actionbar.setDisplayHomeAsUpEnabled(true)

        checkFav()

        getTabLayout(username)

        binding.favbutton.setOnClickListener {
            favButton(username)
        }

    }

    private fun checkFav() {
        val check = contentResolver.query(uri, null, null,null,null)
        while (check?.moveToNext() == true){
            val index0 = check.getColumnIndex(UserContract.UserColumns._ID)
            val index1 = check.getInt(index0)
            Log.d(TAG, index1.toString())
            if (index1 != null){
                binding.favbutton.isChecked = true
                statusFav = true
            } else{
                binding.favbutton.isChecked = false
                statusFav = false
            }
        }
    }

    private fun favButton(username: String) {
        statusFav = !statusFav
        if (statusFav == true){
            binding.favbutton.isChecked = true
            statusFav = true

            Toast.makeText(this, R.string.fav, Toast.LENGTH_SHORT).show()
            val name = binding.nameReceived.text.toString()
            val company = binding.company.text.toString()
            val location = binding.location.text.toString()
            val repository = binding.repositories.text.toString()
            val following = binding.following.text.toString()
            val followers = binding.followers.text.toString()

            val values =  ContentValues()
            values.put(UserContract.UserColumns.COLUMN_NAME_USERNAME, username)
            values.put(UserContract.UserColumns.COLUMN_NAME_NAME, name)
            values.put(UserContract.UserColumns.COLUMN_NAME_AVA_URL, photo)
            values.put(UserContract.UserColumns.COLUMN_NAME_COMPANY, company)
            values.put(UserContract.UserColumns.COLUMN_NAME_LOCATION, location)
            values.put(UserContract.UserColumns.COLUMN_NAME_REPOS, repository)
            values.put(UserContract.UserColumns.COLUMN_NAME_FOLLG, following)
            values.put(UserContract.UserColumns.COLUMN_NAME_FOLLS, followers)

            contentResolver.insert(UserContract.UserColumns.CONTENT_URI, values)

        }
        else{
            binding.favbutton.isChecked = false
            Toast.makeText(this, R.string.unfav, Toast.LENGTH_SHORT).show()

            statusFav = false
            contentResolver.delete(uri,null,null)
        }

    }

    private fun showDatafromFav() {
        val fav = intent.getParcelableExtra<FavUsers>(EXTRA_FAV) as FavUsers

        binding.nameReceived.text = fav.name.toString()
        binding.userReceived.text = fav.user.toString()
        binding.location.text = fav.loc.toString()
        binding.company.text = fav.company.toString()
        binding.repositories.text = fav.repository.toString()
        binding.following.text = fav.following.toString()
        binding.followers.text = fav.followers.toString()
        Glide
                .with(this)
                .load(fav.photo)
                .centerCrop()
                .into(binding.photoReceived)
        photo = fav.photo.toString()

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
        photo = user.photo.toString()
    }

    private fun getTabLayout(data: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = data
        //Log.d(TAG, data.user.toString())
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.setSelectedTabIndicatorColor(Color.parseColor("#Ff0000"))

        supportActionBar?.elevation = 0f
    }

    //agar navigasi back berfungsi
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}