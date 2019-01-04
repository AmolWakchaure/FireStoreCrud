package com.example.firestorecrud;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //views

    EditText
            titleEt,
            descriptionEt;
    Button
            saveButton,
            showListButton;

    //progress dialog
    ProgressDialog pd;

    //firestore instance
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ambiwakchaure.abw@gmail.com

        getSupportActionBar().setTitle("Add Data");
        initialiseViews();

        //progress dialog
        pd = new ProgressDialog(this);
        //firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get input data
                String title = titleEt.getText().toString();
                String description = descriptionEt.getText().toString();

                //method for upload data
                uploadData(title,description);

            }
        });
        //click button to show data
        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }

    private void uploadData(String title, String description) {

        pd.setTitle("Adding data to Firestore");
        pd.show();

        //random id for each data to be stored
        String id = UUID.randomUUID().toString();

        Map<String,Object> doc = new HashMap<>();

        doc.put("id",id);
        doc.put("title",title);
        doc.put("description",description);

        //add this data
        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this,"Uploaded...",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this,"Uploaded...",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initialiseViews() {

        titleEt = (EditText)findViewById(R.id.titleEt);
        descriptionEt = (EditText)findViewById(R.id.descriptionEt);
        saveButton = (Button)findViewById(R.id.saveButton);
        showListButton = (Button)findViewById(R.id.showListButton);
    }
}
