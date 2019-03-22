package com.example.firestorecrud.other

import android.support.v7.widget.AppCompatEditText
import android.text.TextUtils
import android.widget.EditText

class V
{
    companion object {

        //check editrext empty

        fun checkEditexEmpty(inputData : String,errorMessage : String) : Boolean
        {
            if(TextUtils.isEmpty(inputData))
            {
                F.t(errorMessage)
                return false
            }
            else
            {
                return true
            }
        }
        //validate editext empty field
        fun checkEmpty(editext : EditText, errorMessage : String) : Boolean
        {
            if(TextUtils.isEmpty(editext.text.toString().trim()))
            {
                F.t(errorMessage)
                return false
            }
            else
            {
                return true
            }
        }
    }
}