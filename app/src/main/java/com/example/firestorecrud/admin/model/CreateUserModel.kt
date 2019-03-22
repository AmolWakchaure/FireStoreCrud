package com.example.kuba.admin.model

class CreateUserModel(
    private var uFullName:String,
    private var uUserName:String,
    private var uPassword:String,
    private var uMobile:String,
    private var uType:String)
{
    //uFullName
    fun setuFullName(uFullName : String)
    {
        this.uFullName = uFullName

    }
    fun getuFullName(): String?
    {
        return uFullName

    }
    //uUserName
    fun setuUserName(uUserName : String)
    {
        this.uUserName = uUserName

    }
    fun getuUserName(): String?
    {
        return uUserName

    }
    //uPassword
    fun setuPassword(uPassword : String)
    {
        this.uPassword = uPassword

    }
    fun getuPassword(): String?
    {
        return uPassword

    }
    //uMobile
    fun setuMobile(uMobile : String)
    {
        this.uMobile = uMobile

    }
    fun getuMobile(): String?
    {
        return uMobile

    }
    //uType
    fun setuType(uType : String)
    {
        this.uType = uType

    }
    fun getuType(): String?
    {
        return uType

    }
}