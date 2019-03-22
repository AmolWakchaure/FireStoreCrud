package com.example.firestorecrud.userlist

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.firestorecrud.R
import com.example.firestorecrud.admin.view.CreateUserActivity
import com.example.firestorecrud.location.MainActivity
import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.F
import com.example.firestorecrud.other.MyApplication
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_user_list.*
import java.util.ArrayList

class UserListActivity : AppCompatActivity() {


    lateinit var pd: ProgressDialog
    //declare firebase instance
    lateinit var db : FirebaseFirestore

    internal var modelList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        supportActionBar!!.hide()

        //initialise firebase instance
        db = FirebaseFirestore.getInstance()

        //set on click listners to widgets
        setClickListner()


        //Tm.refreshData(db)
        pd = ProgressDialog(this)
        //fetch user list from firestore
        if(F.isNetworkAvailable())
        {
            getUserList()
        }
        else
        {
            goBack_tv.setText(Constants.CONNECTION)
        }


    }

    private fun getUserList() {


        pd.setTitle("Getting User list, please wait...")
        pd.show()

        db.collection(Constants.TBL_USER)
                .get()
                .addOnCompleteListener { task ->
                    modelList.clear()
                    pd.dismiss()
                    if(task.result!!.isEmpty)
                    {
                        F.t("Oops ! user not found")
                    }
                    else
                    {
                        for (doc in task.result!!)
                        {

                            var id = doc.getString("id");
                            var u_full_name = doc.getString("u_full_name");
                            var u_mobile = doc.getString("u_mobile");
                            var u_password = doc.getString("u_password");
                            var u_type = doc.getString("u_type");
                            var u_user_name = doc.getString("u_user_name");

                            modelList.add(UserModel(id!!,u_full_name!!,u_mobile!!,u_password!!,u_type!!,u_user_name!!))
                        }

                        add_new_user_btn.setVisibility(View.VISIBLE)
                        goBack_tv.setText("Created User List ("+modelList.size+")")
                        //set layout manager to recycler view
                        user_list_rl.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL,false)
                        val adapter = UserListAdapter(modelList);
                        user_list_rl.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                }
                .addOnFailureListener { e ->
                    pd.dismiss()
                    F.t(""+e)
                }

    }

    private fun setClickListner() {

        //add new user
        add_new_user_btn.setOnClickListener {

            startActivity(Intent(this, CreateUserActivity::class.java))
        }
        //back page
        goBack_tv.setOnClickListener {

            finish()
        }
        //check firebase data update or not
        db.collection(Constants.TBL_USER)
                .addSnapshotListener(EventListener { snapshots, e ->
                    if (e != null)
                    {
                        Log.e("TAGTAG_DAA", "listen:error", e)
                        return@EventListener
                    }
                    for (dc in snapshots!!.documentChanges)
                    {
                        when (dc.type)
                        {
                            //when data added to the firestore collection
                            DocumentChange.Type.ADDED -> dataRefreshed(dc,"ADDED")
                            //when data modified in the firestore collection
                            DocumentChange.Type.MODIFIED -> dataRefreshed(dc,"MODIFIED")
                            //when data removed from the firestore collection
                            DocumentChange.Type.REMOVED -> dataRefreshed(dc,"REMOVED")
                        }
                    }
                })
    }

    private fun dataRefreshed(dc: DocumentChange,flag : String) {
        Log.e("TAGTAG_DAA", "New Msg: " + dc.document.toObject(Message::class.java))
        Log.e("TAGTAG_DAA", "ID: " + dc.document.get("id")+" Changes : "+flag)
    }


}
