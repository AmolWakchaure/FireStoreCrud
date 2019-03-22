package com.example.firestorecrud.other;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class A
{
    public static void dataa(FirebaseFirestore db,String user_id)
    {
        db.collection("Users").document(user_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists())
                        {

                        }

                    }
                });
    }
}
