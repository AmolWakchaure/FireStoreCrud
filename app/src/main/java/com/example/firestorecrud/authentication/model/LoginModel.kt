package com.example.firestorecrud.authentication.model

class LoginModel(
        private var uName : String,
        private var uPassword : String)
{
    //set user name
     fun setuName(uName : String)
     {
         this.uName = uName
     }
    //set user password
    fun setuPassword(uPassword : String)
    {
        this.uPassword = uPassword
    }
}