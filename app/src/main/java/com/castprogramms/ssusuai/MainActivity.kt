package com.castprogramms.ssusuai

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.databinding.ActivityMainBinding
import com.castprogramms.ssusuai.tools.ProgressBarRequestListener
import com.castprogramms.ssusuai.tools.Utils.isDarkThemeOn
import com.castprogramms.ssusuai.ui.custombottomnavigationview.HideBehaviorWithBlockChat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by viewModel()
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navHostController: NavController

    override fun onStart() {
        super.onStart()
        val googleAuth = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAuth?.id != null)
            viewModel.getUser(googleAuth.id!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.fab.visibility = View.INVISIBLE
        binding.bottomNavigationView.transform(binding.fab)
//        GoogleSignIn.getClient(this, GoogleSignInOptions.Builder().build()).signOut()
        navHostController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()!!

        binding.fab.setOnClickListener {
            centerBNVClick()
        }
        if (this.isDarkThemeOn()) {
            supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_dark))
        } else {
            supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_standard))
        }

        supportActionBar?.elevation = 0f
        navHostController.let {
            val appBarConfiguration = AppBarConfiguration(navHostController.graph)
            setupActionBarWithNavController(navHostController, appBarConfiguration)
            binding.bottomNavigationView.setupWithNavController(navHostController)
        }
        navHostController.addOnDestinationChangedListener { _, destination, _ ->

            val needHomeButton =
                arrayOf(R.id.newFragment, R.id.chatFragment, R.id.addPersonalChatFragment,
                    R.id.addNewFragment
//                    , R.id.editProfileFragment
                )
            supportActionBar?.setDisplayHomeAsUpEnabled(
                destination.id in needHomeButton
            )
            supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)

            if (destination.id == R.id.chatFragment)
                binding.toolbarImageUser.visibility = View.VISIBLE
            else
                binding.toolbarImageUser.visibility = View.GONE
        }
    }

    fun setHtmlText(text: String) {
        if (isDarkThemeOn()) {
            binding.titleToolbar.setTextColor(Color.parseColor("#F7F9FF"))
        } else {
            binding.titleToolbar.setTextColor(Color.parseColor("#5481D8"))
        }
        binding.titleToolbar.text = text
    }

    fun slideUp() {
        try {
            setIsChat(false)
            ((binding.bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideUp(
                binding.fab
            )
            ((binding.fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideUp(
                binding.bottomNavigationView
            )
        } catch (e: Exception) {
        }
    }

    fun slideDown() {
        try {
            ((binding.bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideDown(
                binding.fab
            )
            ((binding.fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideDown(
                binding.bottomNavigationView
            )
        } catch (e: Exception) {
        }
    }

    fun centerBNVClick() {
        (binding.bottomNavigationView.getChildAt(0) as BottomNavigationMenuView)
            .getChildAt(2).performClick()
    }

    fun setIsChat(isChat: Boolean) {
        ((binding.bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).setIsChat(
            isChat
        )
        ((binding.fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).setIsChat(
            isChat
        )
    }

    fun setCustomImage(img: String) {
        binding.progressBarToolbarImageUser.visibility = View.VISIBLE
        Glide.with(this)
            .load(img)
            .listener(ProgressBarRequestListener(binding.progressBarToolbarImageUser))
            .into(binding.toolbarImageUser)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostController.navigateUp() || super.onSupportNavigateUp()
    }
}