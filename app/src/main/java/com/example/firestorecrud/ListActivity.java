package com.example.firestorecrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    List<Model> modelList = new ArrayList<>();
    private RecyclerView
            listRv;

    private FloatingActionButton
            addBtn;

    RecyclerView.LayoutManager layoutManager;

    //firestore instance
    FirebaseFirestore db;

    CustomAdapter customAdapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db = FirebaseFirestore.getInstance();
        listRv = (RecyclerView)findViewById(R.id.listRv);
        addBtn = (FloatingActionButton)findViewById(R.id.addBtn);

        listRv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        listRv.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);
        
        showData();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ListActivity.this,MainActivity.class));

            }
        });
    }

    private void showData() {

        pd.setTitle("Loading data...");
        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        modelList.clear();
                        pd.dismiss();
                        for(DocumentSnapshot doc : task.getResult())
                        {

                            Model model = new Model(
                                    doc.getString("id"),
                                    doc.getString("title"),
                                    doc.getString("description"));

                            modelList.add(model);
                        }

                        customAdapter = new CustomAdapter(ListActivity.this,modelList,ListActivity.this);
                        listRv.setAdapter(customAdapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(ListActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void deleteData(int index) {


        pd.setTitle("Deleting data...");
        pd.show();

        db.collection("Documents").document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        pd.dismiss();
                        Toast.makeText(ListActivity.this,"Deleted...",Toast.LENGTH_LONG).show();
                        showData();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(ListActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
    }
}
