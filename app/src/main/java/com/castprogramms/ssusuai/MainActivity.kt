package com.castprogramms.ssusuai

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.castprogramms.ssusuai.ui.custombottomnavigationview.FabBottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<CircleImageView>(R.id.fab)
        val bottomNavigationView = findViewById<FabBottomNavigationView>(R.id.bottomNavigationView)
        fab.visibility = View.INVISIBLE
        fab.setBackgroundDrawable(getDrawable(R.drawable.fab_bnv))
        bottomNavigationView.transform(fab)
//        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_standard))
        val navHostController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

        navHostController?.let {
            val appBarConfiguration = AppBarConfiguration(navHostController.graph)
            setupActionBarWithNavController(navHostController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navHostController)
        }
    }
}