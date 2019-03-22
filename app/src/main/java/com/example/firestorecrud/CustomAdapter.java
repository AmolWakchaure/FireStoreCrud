package com.example.firestorecrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    public CustomAdapter(ListActivity listActivity, List<Model> modelList, Context context)
    {
        this.listActivity = listActivity;
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

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
            public void onItemLongClick(View view, final int position) {

                //create alert dialog for
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                //options to dislay in dialog
                String [] options = {"Update","Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0)
                        {
                            //update is clicked
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String description = modelList.get(position).getDescription();

                            //intent to start activity
                            Intent i = new Intent(listActivity,MainActivity.class);

                            i.putExtra("pId",id);
                            i.putExtra("pTitle",title);
                            i.putExtra("pDescription",description);

                            listActivity.startActivity(i);
                        }
                        if(which == 1)
                        {
                            //delete is clicked
                            listActivity.deleteData(position);
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        //bind views / set data
        viewHolder.titleText.setText(modelList.get(i).getTitle());
        viewHolder.descriptionText.setText(modelList.get(i).getDescription());
    }

    @Override
    public int getItemCount()
    {
        return modelList.size();
    }
}
