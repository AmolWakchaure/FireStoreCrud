package com.example.firestorecrud.admin.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.firestorecrud.admin.view.CreateUserActivity

class CreateUserFactory (private val listner : CreateUserCallbacks, val context : CreateUserActivity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateUserViewmodel (listner,context) as T
    }
}