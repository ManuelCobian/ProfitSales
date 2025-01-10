package com.luvsoft.profitsales.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.luvsoft.profitsales.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fadeIn = ObjectAnimator.ofFloat(binding.splashImage, "alpha", 0f, 1f).apply {
            duration = 1000
        }
        fadeIn.start()

        fadeIn.doOnEnd {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}