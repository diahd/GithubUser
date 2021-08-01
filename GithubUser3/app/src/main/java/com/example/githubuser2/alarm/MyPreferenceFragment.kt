package com.example.githubuser2.alarm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuser2.R

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var on: String
    private lateinit var switch: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)

        on = resources.getString(R.string.alarmOn)

        switch = findPreference<SwitchPreference>(on) as SwitchPreference

        setSummary()

        alarmReceiver = AlarmReceiver()

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == on){
            switch.isChecked = sharedPreferences.getBoolean(on, false)
            if(switch.isChecked == true ){
                context?.let { alarmReceiver.setRepeatingAlarm(it, AlarmReceiver.TYPE_REPEATING, "Daily Reminder") }
            }else{
                context?.let { alarmReceiver.cancelAlarm(it) }
            }

        }
    }

    private fun setSummary() {
        val sh = preferenceManager.sharedPreferences
        switch.isChecked = sh.getBoolean(on, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}