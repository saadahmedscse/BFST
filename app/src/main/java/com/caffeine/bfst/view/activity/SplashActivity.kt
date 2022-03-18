package com.caffeine.bfst.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.caffeine.bfst.R
import com.caffeine.bfst.utils.Constants

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (Constants.currentUser != null){
                Constants.intentToActivity(this, AuthenticationActivity::class.java)
            }
            else Constants.intentToActivity(this, AuthenticationActivity::class.java)
        }, 0)
    }
}