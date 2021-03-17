package com.kei.ntmdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_third_on_boarding.*

class ThirdOnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_on_boarding)
        supportActionBar?.hide()

        btn_get_started.setOnClickListener{
            val moveActivityIntent = Intent(this, SignInActivity::class.java)
            startActivity(moveActivityIntent)
        }
    }
}