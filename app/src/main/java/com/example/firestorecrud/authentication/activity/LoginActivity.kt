package com.example.firestorecrud.authentication.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.firestorecrud.R
import com.example.firestorecrud.admin.view.CreateUserActivity
import com.example.firestorecrud.admin.viewmodel.CreateUserViewmodel
import com.example.firestorecrud.homepage.HomeActivity
import com.example.firestorecrud.location.MainActivity
import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.F
import com.example.firestorecrud.other.MyApplication
import com.example.firestorecrud.other.V
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{

    //firestore instance
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()
        //firestore
        db = FirebaseFirestore.getInstance()
        setClickListener()
    }

    private fun setClickListener() {

        //sign in
        signin_btn.setOnClickListener {

            //first validate all fields
            validate();

        }
        //register
        register_btn.setOnClickListener {

            startActivity(Intent(this,CreateUserActivity::class.java))
            finish()
        }
    }

    private fun validate()
    {

        if(F.isNetworkAvailable())
        {
            if(!V.checkEmpty(user_name_et,"Oops ! enter username"))
            {
                return
            }
            if(!V.checkEmpty(password_et,"Oops ! enter password"))
            {
                return
            }
            //ready to verify login
            userLogin(user_name_et.text.toString(),password_et.text.toString())
        }
        else
        {
            F.t(Constants.CONNECTION)
        }

    }
    private fun userLogin(username : String,password : String)
    {
        try
        {
            val mQuery = db.collection(Constants.TBL_USER)
                    .whereEqualTo(Constants.U_USER_NAME, username)
                    .whereEqualTo(Constants.U_PASWORD, password)

            mQuery.addSnapshotListener { queryDocumentSnapshots, e ->

                if(queryDocumentSnapshots!!.isEmpty)
                {
                    F.t("Oops ! account not found for this input")
                }
                else
                {
                    for (ds in queryDocumentSnapshots!!)
                    {
                        if (ds.exists())
                        {

                            MyApplication.editor.putString(Constants.U_USER_ID,ds.id).commit()
                            MyApplication.editor.putString(Constants.U_USER_NAME,username).commit()
                            MyApplication.editor.putString(Constants.U_PASWORD,password).commit()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                            break

                        }
                    }
                }

            }
        }
        catch (e : Exception)
        {
            F.t("userLogin : "+e)
            e.printStackTrace()
        }

    }
}
