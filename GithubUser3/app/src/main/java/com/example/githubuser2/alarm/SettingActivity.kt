package com.example.githubuser2.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import com.example.githubuser2.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val actionbar = supportActionBar
        val titleBar = resources.getString(R.string.setting)
        actionbar!!.title = titleBar
        actionbar.setDisplayHomeAsUpEnabled(true)

        val setting = findViewById<TextView>(R.id.edtLanguage)

        setting.setOnClickListener{
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_preferences, MyPreferenceFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}