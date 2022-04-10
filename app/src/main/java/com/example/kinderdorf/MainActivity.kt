package com.example.kinderdorf

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kinderdorf.fragments.CalendarFragment
import com.example.kinderdorf.fragments.HomeFragment
import com.example.kinderdorf.fragments.MessageFragment
import com.example.kinderdorf.fragments.ProfileFragment
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item->

            var fragmentToShow: Fragment? = null

            when(item.itemId){
                R.id.action_home -> {
                    fragmentToShow = HomeFragment()
//                    Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.action_calendar -> {
                    fragmentToShow = CalendarFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
                R.id.action_message -> {
                    fragmentToShow = MessageFragment()
                }
                R.id.action_logout -> {
                    ParseUser.logOut()
                    goToLoginActivity()
                }
            }
            if(fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}