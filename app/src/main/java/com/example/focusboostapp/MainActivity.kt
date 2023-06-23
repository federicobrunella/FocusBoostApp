package com.example.focusboostapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        findViewById<ImageView>(R.id.imageMenu).setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        var navigationView : NavigationView = findViewById(R.id.navigationView)

        var navController: NavController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(navigationView, navController)

        firebaseAuth = FirebaseAuth.getInstance()
        navigationView.getHeaderView(0).findViewById<TextView>(R.id.userTextView).text = firebaseAuth.currentUser?.email.toString()
    }
}