package com.dicoding.skivent.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.skivent.UserPreference
import com.dicoding.skivent.databinding.ActivitySplashScreenBinding
import com.dicoding.skivent.ui.authentication.FactoryViewModel
import com.dicoding.skivent.ui.authentication.Login
import com.dicoding.skivent.ui.authentication.UserLoginViewModel
import com.dicoding.skivent.ui.authentication.dataStore
import com.dicoding.skivent.ui.dashboard.MainActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreference.getInstance(dataStore)
        val loginViewModel =
            ViewModelProvider(this, FactoryViewModel(pref))[UserLoginViewModel::class.java]
        loginViewModel.getLoginSession().observe(this) { isLoggedIn ->
            val splashScreenTextLogo =
                ObjectAnimator.ofFloat(binding.tvSplashScreen, View.ALPHA, 1f).setDuration(2000)
            AnimatorSet().apply {
                playTogether(splashScreenTextLogo)
                start()
            }
            val intent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, Login::class.java)
            }
            binding.logoGambar.animate()
                .setDuration(3000)
                .alpha(0f)
                .withEndAction {
                    startActivity(intent)
                    finish()
                }
            binding.logoNama.animate()
                .setDuration(3000)
                .alpha(1f)
                .withEndAction {
                    startActivity(intent)
                    finish()
                }
        }
    }
}