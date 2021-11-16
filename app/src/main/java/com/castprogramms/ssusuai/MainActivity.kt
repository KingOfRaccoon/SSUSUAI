package com.castprogramms.ssusuai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.castprogramms.ssusuai.ui.custombottomnavigationview.FabBottomNavigationView
import com.castprogramms.ssusuai.ui.custombottomnavigationview.HideBehaviorWithBlockChat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.Exception

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by viewModel()
    private lateinit var bottomNavigationView : FabBottomNavigationView
    private lateinit var fab: CircleImageView
    private lateinit var navHostController: NavController

    override fun onStart() {
        super.onStart()
        val googleAuth = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAuth != null)
            viewModel.getUser(googleAuth.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)
        fab.visibility = View.INVISIBLE
        bottomNavigationView.transform(fab)
        navHostController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()!!

        fab.setOnClickListener {
            centerBNVClick()
        }
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_standard))
        supportActionBar?.elevation = 0f
        navHostController.let {
            val appBarConfiguration = AppBarConfiguration(navHostController.graph)
            setupActionBarWithNavController(navHostController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navHostController)
        }
        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            val needHomeButton = arrayOf(R.id.newFragment, R.id.chatFragment, R.id.addPersonalChatFragment)
            supportActionBar?.setDisplayHomeAsUpEnabled(
                destination.id in needHomeButton
            )
            supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        }

//        val arrow = resources.getDrawable(R.drawable.arrow_back)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
    }

    fun setHtmlText(text: String){
        supportActionBar?.title =
            Html.fromHtml("<font color='#5481D8'>$text</font>")
    }

    fun slideUp(){
        try {
            setIsChat(false)
            ((bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideUp(fab)
            ((fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideUp(bottomNavigationView)
        }catch (e: Exception){}
    }

    fun slideDown(){
        try {
            ((bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideDown(fab)
            ((fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).slideDown(bottomNavigationView)
        }catch (e: Exception){}
    }

    fun centerBNVClick(){
        (bottomNavigationView.getChildAt(0) as BottomNavigationMenuView)
            .getChildAt(2).performClick()
    }

    fun setIsChat(isChat: Boolean){
        ((bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).setIsChat(isChat)
        ((fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBehaviorWithBlockChat).setIsChat(isChat)
    }
}