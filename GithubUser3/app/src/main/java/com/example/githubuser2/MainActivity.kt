package com.example.githubuser2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.alarm.SettingActivity
import com.example.githubuser2.data.Users
import com.example.githubuser2.data.UsersAdapter
import com.example.githubuser2.databinding.ActivityMainBinding
import com.example.githubuser2.favorite.FavUserActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var users = ArrayList<Users>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var name: String
    private lateinit var location: String
    private lateinit var repos: String
    private lateinit var folls: String
    private lateinit var follg: String
    private lateinit var comp: String
    private lateinit var username: String
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionbar = supportActionBar
        actionbar?.title = resources.getString(R.string.app_name)

        binding.searchAnim.playAnimation()
        binding.progressBar.visibility = View.INVISIBLE

        searchItem()
    }

    private fun searchItem() {
        binding.searchView.queryHint = resources.getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isEmpty()){
                    return true
                }else{
                    users.clear()
                    getUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getUsers(search : String) {
        binding.searchAnim.visibility = View.GONE
        binding.progressBar.playAnimation()
        binding.progressBar.visibility = View.VISIBLE

        val asyncClient = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$search"
        asyncClient.addHeader("Authorization", "token e1cfcb168bacde5970dc0c2bfc770b13778080f2")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int,
                                   headers: Array<Header>?,
                                   responseBody: ByteArray) {
                //Jika koneksi berhasil
                binding.progressBar.visibility = View.INVISIBLE

                //parsing JSON
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    //Log.d(TAG, result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        username = item.getString("login")
                        getUsersDetail(username)
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int,
                                   headers: Array<Header>?,
                                   responseBody: ByteArray?,
                                   error: Throwable?) {
                //jika koneksi gagal
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUsersDetail(id: String){
        //Parsing JSON2
        val url2 = "https://api.github.com/users/$id"
        val client2 = AsyncHttpClient()
        client2.addHeader("Authorization", "token e1cfcb168bacde5970dc0c2bfc770b13778080f2")
        client2.addHeader("User-Agent", "request")
        client2.get(url2, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int,
                                   headers: Array<Header>?,
                                   responseBody: ByteArray) {
                //parsing JSON2
                val result2 = String(responseBody)
                val responseObject2 = JSONObject(result2)
                avatar = responseObject2.getString("avatar_url")
                username = responseObject2.getString("login")
                name = responseObject2.getString("name")
                location = responseObject2.getString("location")
                repos = responseObject2.getString("public_repos")
                folls = responseObject2.getString("followers")
                follg = responseObject2.getString("following")
                comp = responseObject2.getString("company")

                val user = Users(avatar, name, username, comp, location, follg, repos, folls)
                users.add(user)
                showRecyclerList()

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listCountryAdapter = UsersAdapter(users)
        binding.rvUsers.adapter = listCountryAdapter

        listCountryAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                val moveActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
                moveActivity.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(moveActivity)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.language_settings ->{
                val mIntent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(mIntent)

            }

            R.id.fav_page ->{
                val mIntent = Intent(this@MainActivity, FavUserActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}