package com.example.firestorecrud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder>
{

    ListActivity listActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList, Context context) {
        this.listActivity = listActivity;
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here

        viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(context,modelList.get(position).getTitle()+"\n"+modelList.get(position).getDescription(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        //bind views / set data
        viewHolder.titleText.setText(modelList.get(i).getTitle());
        viewHolder.descriptionText.setText(modelList.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
