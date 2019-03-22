package com.example.firestorecrud.admin.viewmodel

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.F
import com.example.firestorecrud.other.V
import com.example.kuba.admin.model.CreateUserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import android.widget.Toast
import com.google.firebase.firestore.QuerySnapshot
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.example.firestorecrud.other.MyApplication


class CreateUserViewmodel(private val listner : CreateUserCallbacks,private val context: Context) : ViewModel()
{

    //initialise createUserModel
    private val createUserModel : CreateUserModel
    //firestore instance
    private val db: FirebaseFirestore

    var userNameStatus : Boolean
    //init constructor
    init {

        this.createUserModel = CreateUserModel("","","","","")
        //firestore
        db = FirebaseFirestore.getInstance()
        userNameStatus = false

        //listner.onUserCreatedFail("disable")
    }

    //uFullName
    val uFullNameTextwatcher : TextWatcher
        get() = object : TextWatcher
        {
            override fun afterTextChanged(uFullName: Editable?)
            {

                createUserModel.setuFullName(uFullName.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        }
    //uUserName
    val uUserNameTextwatcher : TextWatcher
        get() = object : TextWatcher
        {
            override fun afterTextChanged(uUserName: Editable?)
            {

                createUserModel.setuUserName(uUserName.toString())


            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        }
    //uPassword
    val uPasswordTextwatcher : TextWatcher
        get() = object : TextWatcher
        {
            override fun afterTextChanged(uPassword: Editable?)
            {

                createUserModel.setuPassword(uPassword.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        }
    //uMobile
    val uMobileTextwatcher : TextWatcher
        get() = object : TextWatcher
        {
            override fun afterTextChanged(uMobile: Editable?)
            {

                createUserModel.setuMobile(uMobile.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        }
    //uType
    val uTypeTextwatcher : TextWatcher
        get() = object : TextWatcher
        {
            override fun afterTextChanged(uType: Editable?)
            {

                createUserModel.setuType(uType.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        }

    //check username already exists
    fun onCheckUserNameExists(view : View)
    {
        var uUserName = createUserModel.getuUserName()
        //uUserName
        if(!V.checkEditexEmpty(uUserName!!,"Enter user name"))
        {
            return
        }
        userNameExistsAlready(uUserName!!, object : ReturnData {
            override fun returnInfo(status: Boolean)
            {
                F.e("userNameExistsAlready : "+status)
                /*if(status)
                {
                    listner.onUserCreatedFail("disable")
                }
                else
                {
                    listner.onUserCreatedFail("enable")
                }*/

            }
        })
    }
    //for perform on submit details
    fun onSubmitUserDetailsClicked(view : View)
    {
        var uFullName = createUserModel.getuFullName()
        var uUserName = createUserModel.getuUserName()
        var uPassword = createUserModel.getuPassword()
        var uMobile = createUserModel.getuMobile()
        var uType = createUserModel.getuType()

        //uFullName
        if(!V.checkEditexEmpty(uFullName!!,"Enter full name"))
        {
            return
        }
        //uUserName
        if(!V.checkEditexEmpty(uUserName!!,"Enter user name"))
        {
            return
        }
        //uPassword
        if(!V.checkEditexEmpty(uPassword!!,"Enter password name"))
        {
            return
        }
        //uMobile
        if(!V.checkEditexEmpty(uMobile!!,"Enter mobile name"))
        {
            return
        }
        //uType
        if(!V.checkEditexEmpty(uType!!,"Enter type name"))
        {
            return
        }

        //check internet available
        if(F.isNetworkAvailable())
        {


            //check user already exists
            val query = db.collection(Constants.TBL_USER).whereEqualTo(Constants.U_USER_NAME, uUserName)
            query.get().addOnCompleteListener { task ->
                if (Objects.requireNonNull(task.result)!!.size() > 0)
                {
                    //already user exists
                    F.t("User name already exist in the database ")
                }
                else
                {
                    //add a new user to Firestore database
                    //if user not found then ready to register
                    submitUserDetails(uFullName,uUserName,uPassword,uMobile,uType)
                }
            }


        }
        else
        {
            F.t(Constants.CONNECTION)
        }
    }


    private fun userNameExistsAlready(username : String,sfsdf : ReturnData)
    {
        try
        {
            val mQuery = db.collection(Constants.TBL_USER)
                    .whereEqualTo(Constants.U_USER_NAME, username)

            mQuery.addSnapshotListener { queryDocumentSnapshots, e ->

                if(queryDocumentSnapshots!!.isEmpty)
                {
                    sfsdf.returnInfo(false)
                }
                else
                {
                    for (ds in queryDocumentSnapshots!!)
                    {
                        if (ds.exists())
                        {
                            sfsdf.returnInfo(true)
                            break

                        }
                    }
                }

            }
        }
        catch (e : Exception)
        {
            sfsdf.returnInfo(false)
        }

    }
    interface  ReturnData
    {
        fun returnInfo(status : Boolean)
    }
    private fun submitUserDetails(
        uFullName: String,
        uUserName: String,
        uPassword: String,
        uMobile: String,
        uType: String
    )
    {

        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Creating User...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        try
        {

            //random id for each data to be stored
            val id = UUID.randomUUID().toString()

            val doc = HashMap<String, Any>()

            doc[Constants.ID] = id
            doc[Constants.U_FULLNAME] = uFullName
            doc[Constants.U_USER_NAME] = uUserName
            doc[Constants.U_PASWORD] = uPassword
            doc[Constants.U_MOBILE] = uMobile
            doc[Constants.U_TYPE] = uType
            //add this data
            db.collection(Constants.TBL_USER).document(id).set(doc)
                .addOnCompleteListener(OnCompleteListener<Void> {
                    progressDialog.dismiss()

                    listner.onUserCreatedSucces("fdgf")
                    F.t("User created successfully...")
                })
                .addOnFailureListener(OnFailureListener {
                    progressDialog.dismiss()
                    F.t("Fail to create User...")
                })
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }

    }

}