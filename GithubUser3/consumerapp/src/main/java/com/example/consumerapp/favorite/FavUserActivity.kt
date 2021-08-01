package com.example.consumerapp.favorite

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.DetailUserActivity
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ActivityFavUserBinding
import com.example.consumerapp.db.MappingHelper
import com.example.consumerapp.db.UserContract.UserColumns.Companion.CONTENT_URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavUserActivity : AppCompatActivity() {

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var binding: ActivityFavUserBinding
    private lateinit var adapter: FavUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        val titleBar = resources.getString(R.string.fav_page)
        actionbar!!.title = titleBar
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.setHasFixedSize(true)
        adapter = FavUserAdapter(this)
        binding.rvFav.adapter = adapter

        adapter.setOnItemClickCallback(object : FavUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavUsers) {
                val mIntent = Intent(this@FavUserActivity, DetailUserActivity::class.java)
                mIntent.putExtra(DetailUserActivity.EXTRA_FAV, data)
                startActivity(mIntent)
            }
        })
        getDataFav()

        if (savedInstanceState == null) {
            loadDataAsync()
        } else {
            savedInstanceState.getParcelableArrayList<FavUsers>(EXTRA_STATE)?.also { adapter.listFav = it }
        }

    }

    private fun getDataFav() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadDataAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

    }

    private fun loadDataAsync() {
        GlobalScope.launch(Dispatchers.Main){
            val deferredData = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val data = deferredData.await()
            if(data.size > 0){
                adapter.listFav = data
                binding.progressBar.visibility = View.INVISIBLE

            } else{
                adapter.listFav = ArrayList()
                binding.progressBar.visibility = View.VISIBLE
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

   override fun onResume() {
        super.onResume()
        loadDataAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFav)
    }
}