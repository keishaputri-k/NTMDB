package com.kei.ntmdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_first_on_boarding.*

class FirstOnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_on_boarding)
        supportActionBar?.hide()

        tv_next_first.setOnClickListener {
            val moveActivityIntent = Intent(this, SecondOnBoardingActivity::class.java)
            startActivity(moveActivityIntent)
        }
        tv_skip_first.setOnClickListener {
            val moveActivityIntent = Intent(this, SignInActivity::class.java)
            startActivity(moveActivityIntent)
        }
    }
}