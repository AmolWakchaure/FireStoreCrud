package com.example.firestorecrud.userlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.firestorecrud.R

class UserListAdapter (val userList: ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int
    {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(user: UserModel)
        {
            val id_tv = itemView.findViewById(R.id.id_tv) as TextView
            val u_full_name_tv = itemView.findViewById(R.id.u_full_name_tv) as TextView
            val u_mobile_tv = itemView.findViewById(R.id.u_mobile_tv) as TextView
            val u_password_tv = itemView.findViewById(R.id.u_password_tv) as TextView
            val u_type_tv = itemView.findViewById(R.id.u_type_tv) as TextView
            val u_user_name_tv = itemView.findViewById(R.id.u_user_name_tv) as TextView

            id_tv.text = user.id
            u_full_name_tv.text = user.u_full_name
            u_mobile_tv.text = user.u_mobile
            u_password_tv.text = user.u_password
            u_type_tv.text = user.u_type
            u_user_name_tv.text = user.u_user_name

        }


    }
}