package com.example.firestorecrud.other

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDex

class MyApplication : Application()
{

    companion object {

        lateinit var context : Context
        lateinit var prefs: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()


        //initialize context
        context = applicationContext

        //initialise shared preferences
        prefs = getSharedPreferences(Constants.PREFS_KEY,0)
        editor = prefs.edit()
        editor.commit()

    }
}