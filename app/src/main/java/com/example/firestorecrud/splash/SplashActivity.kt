package com.example.firestorecrud.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.firestorecrud.R
import com.example.firestorecrud.authentication.activity.LoginActivity
import com.example.firestorecrud.homepage.HomeActivity
import com.example.firestorecrud.location.MainActivity
import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.MyApplication

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //check page navigation
        var userId = MyApplication.prefs.getString(Constants.U_USER_ID,Constants.ZERO)
        if(userId.equals(Constants.ZERO))
        {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}
