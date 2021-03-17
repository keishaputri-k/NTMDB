package com.kei.ntmdb.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener{
    companion object {
        fun getLaunchService (from: Context) = Intent(from, ProfileActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        tv_logout.setOnClickListener(this)
        iv_back_profile.setOnClickListener(this)
    }
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.tv_logout -> logout()
            R.id.iv_back_profile -> startActivity(Intent(MainActivity.getLaunchService(this)))
        }
    }
    private fun logout() {
        startActivity(Intent(
                SignInActivity.getLaunchService(
                        this)
        ))
        FirebaseAuth.getInstance().signOut()
    }
}