package com.castprogramms.ssusuai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.castprogramms.ssusuai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.fab.setOnClickListener {
            Log.e("data", binding.fab.isVisible.toString())
            binding.bottomNavigationView.transform(binding.fab)
        }
    }
}