package com.example.firestorecrud.other

interface Constants {

    companion object {


        //shared preferences
        var PREFS_KEY : String = "prefs_key"
        var U_USER_ID : String = "u_user_id"
        var ZERO : String = "0"
        var ADDED : String = "added"

        //firebase tables
        var TBL_USER : String = "tbl_user"
        var TBL_USER_LOCATION : String = "tbl_user_location"

        //TBL_USER
        var ID : String = "id"
        var U_FULLNAME : String = "u_full_name"
        var U_USER_NAME : String = "u_user_name"
        var U_PASWORD : String = "u_password"
        var U_MOBILE : String = "u_mobile"
        var U_TYPE : String = "u_type"

        //TBL_USER_LOCATION
        //var ID : String = "id"
        //var U_USER_ID : String = "u_user_id"
        var U_LATITUDE : String = "latitude"
        var U_LONGITUDE : String = "longitude"
        var CREATED_DATE_TIME : String = "timeStamp"


        var CONNECTION : String = "Oops ! internet connection off"

    }
}