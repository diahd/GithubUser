package com.example.githubuser

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataUser: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var dataLoc: Array<String>
    private lateinit var dataCom: Array<String>
    private lateinit var dataRep: Array<String>
    private lateinit var dataFollg: Array<String>
    private lateinit var dataFolls: Array<String>
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.lv_list)
        val actionbar = supportActionBar
        actionbar!!.title = "Github User"
        actionbar.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter(this)

        listView.adapter = adapter

        prepare()
        addItem()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedUser = users[position]

            val moveActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
            moveActivity.putExtra(DetailUserActivity.EXTRA_USER, selectedUser)
            startActivity(moveActivity)

        }
    }

    private fun prepare(){
        dataName = resources.getStringArray(R.array.name)
        dataUser = resources.getStringArray(R.array.username)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataCom = resources.getStringArray(R.array.company)
        dataLoc = resources.getStringArray(R.array.location)
        dataRep = resources.getStringArray(R.array.repository)
        dataFollg = resources.getStringArray(R.array.following)
        dataFolls = resources.getStringArray(R.array.followers)

    }

    private fun addItem(){
        for (position in dataName.indices){
            val user = User(
                dataPhoto.getResourceId(position, 1),
                dataName[position],
                dataUser[position],
                dataCom[position],
                dataLoc[position],
                dataRep[position],
                dataFollg[position],
                dataFolls[position],
            )
            users.add(user)
        }
        adapter.users = users
    }
}