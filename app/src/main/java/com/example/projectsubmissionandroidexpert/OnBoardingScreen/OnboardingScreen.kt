package com.example.projectsubmissionandroidexpert.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsubmissionandroidexpert.R
import com.example.projectsubmissionandroidexpert.databinding.ActivityOnboardingScreenBinding
import com.example.projectsubmissionandroidexpert.main.MainActivity

class OnboardingScreen : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLearniAgents.setOnClickListener {
            navigateMainActivity(this)
        }
    }
    private fun navigateMainActivity (context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        finish()
    }
}