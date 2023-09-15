package com.example.apiintegrationv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navFrag) as NavHostFragment
        navController = navHostFragment.navController



        findViewById<Button>(R.id.homeBtn).setOnClickListener {
            val intent = Intent(this, this@MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
            navController.popBackStack(R.id.mainFragment, false)
        }
        findViewById<Button>(R.id.backBtn).setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            if (fm.backStackEntryCount > 0) {
                navController.popBackStack()
            } else {
                onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        lateinit var language: String
    }
}