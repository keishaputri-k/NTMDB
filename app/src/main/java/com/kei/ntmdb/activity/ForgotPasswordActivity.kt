package com.kei.ntmdb.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var mAuth: FirebaseAuth
    companion object {
        fun getLaunchService (from: Context) = Intent(from, ForgotPasswordActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        btn_back_forgot.setOnClickListener(this)
        btn_forgot.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
    }
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_back_forgot -> startActivity(
                SignInActivity.getLaunchService(this)
            )
            R.id.btn_forgot -> forgotPassword()
        }
    }
    private fun forgotPassword() {
        val email = et_email_forgot.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,getString(R.string.txt_error), Toast.LENGTH_SHORT).show()
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Check Email to reset password", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(
                        SignInActivity.getLaunchService(
                            this
                        )
                    ))
                }else{
                    Toast.makeText(this,"Failed to reset password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}