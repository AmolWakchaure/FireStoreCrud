package com.example.firestorecrud;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView
            titleText,
            descriptionText;

    public View
            mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListner.onItemClick(v,getAdapterPosition());
            }
        });

        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mClickListner.onItemLongClick(v,getAdapterPosition());
                return false;
            }
        });

        //initialise views
        titleText = (TextView)mView.findViewById(R.id.titleText);
        descriptionText = (TextView)mView.findViewById(R.id.titleDescription);

    }

    private ViewHolder.ClickListner mClickListner;
    //interface for click listner
    public interface ClickListner
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListner(ViewHolder.ClickListner clickListner)
    {
        mClickListner = clickListner;
    }
}
