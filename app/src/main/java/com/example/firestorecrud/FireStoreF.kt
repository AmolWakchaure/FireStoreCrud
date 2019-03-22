package com.example.firestorecrud

import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.F
import com.example.firestorecrud.other.MyApplication
import com.example.firestorecrud.other.T
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FireStoreF
{
    companion object {



        fun addDataInFireStore(db : FirebaseFirestore, latitude : String, longitude : String)
        {
            try
            {

                //random id for each data to be stored
                val id = UUID.randomUUID().toString()

                val doc = HashMap<String, Any>()

                doc[Constants.ID] = id
                doc[Constants.U_USER_ID] = MyApplication.prefs.getString(Constants.U_USER_ID,"0")
                doc[Constants.U_LATITUDE] = latitude
                doc[Constants.U_LONGITUDE] = longitude
                doc[Constants.CREATED_DATE_TIME] = T.getSystemDateTimeTwentyFour()

                //add this data
                db.collection(Constants.TBL_USER_LOCATION).document(id).set(doc)
                        .addOnCompleteListener(OnCompleteListener<Void> {

                            F.e("latlong added...")

                        })
                        .addOnFailureListener(OnFailureListener {

                            F.e("latlong adding fail...")
                        })
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        }
    }
}