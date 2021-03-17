package com.kei.ntmdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_second_on_boarding.*

class SecondOnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_on_boarding)
        supportActionBar?.hide()

        tv_next_second.setOnClickListener {
            val moveActivityIntent = Intent(this, ThirdOnBoardingActivity::class.java)
            startActivity(moveActivityIntent)
        }
        tv_skip_second.setOnClickListener {
            val moveActivityIntent = Intent(this, SignInActivity::class.java)
            startActivity(moveActivityIntent)
        }
    }
}