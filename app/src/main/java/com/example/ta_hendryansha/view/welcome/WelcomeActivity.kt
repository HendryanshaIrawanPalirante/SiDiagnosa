package com.example.ta_hendryansha.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.view.login.LoginActivity
import com.example.ta_hendryansha.view.signup.SignupActivity

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnDaftar: Button = findViewById(R.id.daftar)
        val btnLogin: Button = findViewById(R.id.login)

        btnDaftar.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.daftar ->{
                val intent = Intent(this@WelcomeActivity, SignupActivity::class.java)
                startActivity(intent)
            }
            R.id.login ->{
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}