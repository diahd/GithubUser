package com.example.githubuser


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val photoReceived: CircleImageView = findViewById(R.id.photo_received)
        val nameReceived: TextView = findViewById(R.id.name_received)
        val userReceived: TextView = findViewById(R.id.user_received)
        val locReceived: TextView = findViewById(R.id.location)
        val comReceived: TextView = findViewById(R.id.company)
        val repReceived: TextView = findViewById(R.id.repositories)
        val follgReceived: TextView = findViewById(R.id.following)
        val follsReceived: TextView = findViewById(R.id.followers)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        nameReceived.text = user.name.toString()
        userReceived.text = user.user.toString()
        locReceived.text = user.loc.toString()
        comReceived.text = user.company.toString()
        repReceived.text = user.repository.toString()
        follgReceived.text = user.following.toString()
        follsReceived.text = user.followers.toString()
        Glide
            .with(this)
            .load(user.photo)
            .centerCrop()
            .into(photoReceived)
    }
}