package com.example.firestorecrud.other;

import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class T
{
    public static String getSystemDateTimeTwentyFour()
    {

        String systemTime = null;

        try
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            systemTime = df.format(Calendar.getInstance().getTime());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return systemTime;

    }
    public static boolean userNameExistsAlready(FirebaseFirestore db,String username)
    {
        boolean status = false;
        try
        {
            Query mQuery = db.collection(Constants.Companion.getTBL_USER())
                    .whereEqualTo(Constants.Companion.getU_USER_NAME(), username);

            mQuery.addSnapshotListener(new EventListener<QuerySnapshot>()
            {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e)
                {
                    for (DocumentSnapshot ds: queryDocumentSnapshots)
                    {

                        if (ds.exists())
                        {

                            F.Companion.t("found");
                        }

                    }
                }
            });
            status = false;
        }
        catch (Exception e)
        {
            F.Companion.t("not found");
        }

        return status;
    }
}
