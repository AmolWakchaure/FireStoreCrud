package com.example.firestorecrud.other

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast

class F
{
    companion object {

        //function for display toast
        fun t(message : String)
        {
            Toast.makeText(MyApplication.context,message,Toast.LENGTH_LONG).show()
        }
        //function for displya error log
        fun e(message : String)
        {
            Log.e("my_vts_log",message)
        }
        //function for display toast
        fun s(view : View,message : String)
        {
           Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
        }

        //check internet available
        fun isNetworkAvailable(): Boolean
        {
            val connectivityManager = MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }

    }
}