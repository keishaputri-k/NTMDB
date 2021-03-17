package com.kei.ntmdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kei.ntmdb.R


class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(
                Intent(this,
                    FirstOnBoardingActivity::class.java)
            )
            finish()
        }, SPLASH_TIME_OUT)
    }
}