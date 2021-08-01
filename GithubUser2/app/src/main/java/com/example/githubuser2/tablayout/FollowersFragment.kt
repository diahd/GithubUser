package com.example.githubuser2.tablayout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.R
import com.example.githubuser2.data.Users
import com.example.githubuser2.databinding.FragmentFollowersBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowersFragment : Fragment() {

    private var users = ArrayList<Users>()
    private lateinit var binding: FragmentFollowersBinding

    companion object{
        private const val ARG_USERNAME = "username"
        private val TAG = FollowersFragment::class.java.simpleName

        fun newInstance(username: String?): FollowersFragment{
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFollowersBinding.bind(view)

        getFollowers()
    }

    private fun getFollowers() {
        binding.progressBar.visibility = View.VISIBLE
        val username = arguments?.getString(ARG_USERNAME)
        Log.d(TAG, username!!)

        //parsing JSON
        val asyncClient = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        asyncClient.addHeader("Authorization", "token e1cfcb168bacde5970dc0c2bfc770b13778080f2")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val login = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        Log.d(TAG, login)
                        val user = Users(avatar, null, login, null, null, null,null ,null )
                        users.add(user)

                        showRecyclerList()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showRecyclerList() {
        binding.listFollowers.layoutManager = LinearLayoutManager(activity)
        val listFollowersAdapter = FollowersAdapter(users)
        binding.listFollowers.adapter = listFollowersAdapter
    }

}