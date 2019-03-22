package com.example.firestorecrud.admin.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.firestorecrud.R
import com.example.firestorecrud.admin.viewmodel.CreateUserCallbacks
import com.example.firestorecrud.admin.viewmodel.CreateUserFactory
import com.example.firestorecrud.admin.viewmodel.CreateUserViewmodel
import com.example.firestorecrud.authentication.activity.LoginActivity
import com.example.firestorecrud.databinding.ActivityCreateUserBinding
import com.example.firestorecrud.other.F
import kotlinx.android.synthetic.main.activity_create_user.*


class CreateUserActivity : AppCompatActivity(), CreateUserCallbacks {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        supportActionBar!!.setTitle("Register New User")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val activityLogin = DataBindingUtil.setContentView<ActivityCreateUserBinding>(this,R.layout.activity_create_user)
       //insatantiate new ViewModels
        activityLogin.createUserViewModel = ViewModelProviders.of(this, CreateUserFactory(this,this)).get(CreateUserViewmodel::class.java)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_d, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onUserCreatedSucces(message: String)
    {

        var i = Intent(this, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
    }
    override fun onUserCreatedFail(message: String)
    {
        if(message.equals("disable"))
        {
            password_et.setVisibility(View.GONE);
            mobile_et.setVisibility(View.GONE);
            type_et.setVisibility(View.GONE);
        }
        else if(message.equals("enable"))
        {
            password_et.setVisibility(View.VISIBLE);
            mobile_et.setVisibility(View.VISIBLE);
            type_et.setVisibility(View.VISIBLE);
        }
        else
        {

        }


    }
}
