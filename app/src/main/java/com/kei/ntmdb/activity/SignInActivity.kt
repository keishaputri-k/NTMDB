package com.kei.ntmdb.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kei.ntmdb.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN: Int = 1

    companion object {
        fun getLaunchService (from: Context) = Intent(from, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()
        configurationGoogleSignIn()
        setUpRequestUI()
        firebaseAuth = FirebaseAuth.getInstance()
        tv_forgot.setOnClickListener(this)
        tv_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.tv_forgot -> startActivity(ForgotPasswordActivity.getLaunchService(this))
            R.id.tv_sign_up -> startActivity(
                    SignUpActivity.getLaunchService(
                            this
                    )
            )
            R.id.btn_login -> loginEmailPass()
        }
    }
    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }

    private fun loginEmailPass() {
        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Insert Complete Data", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(MainActivity.getLaunchService(this))
                return@addOnCompleteListener
            }else{
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            val progress = ProgressDialog(this, R.style.Theme_AppCompat_Light_Dialog)
            progress.hide()
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }catch (e: ApiException){
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setUpRequestUI() {
        btn_google_sign_in.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val intent : Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                startActivity(MainActivity.getLaunchService(this))

            }else{
                Toast.makeText(this, "Google sign in failed ", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun configurationGoogleSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("766228955032-90edke1aoh6f2gkbak00sbldcm30ac03.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}