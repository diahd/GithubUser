package com.example.consumerapp.tablayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import com.example.consumerapp.data.Users
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class FollowersAdapter(private val listFoll: ArrayList<Users>): RecyclerView.Adapter<FollowersAdapter.ListFollsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollsHolder {
        val view: View = LayoutInflater.from (parent.context).inflate(R.layout.item_foll, parent, false)
        return ListFollsHolder(view)
    }

    override fun onBindViewHolder(holder: ListFollsHolder, position: Int) {
        val user = listFoll[position]

        Glide.with(holder.itemView.context)
                .load(user.photo)
                .apply(RequestOptions().override(55,55))
                .into(holder.imgPhoto)

        holder.txtUser.text = user.user
    }

    override fun getItemCount(): Int {
        return listFoll.size
    }

    inner class ListFollsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtUser: TextView = itemView.findViewById(R.id.txt_user2)
        val imgPhoto: CircleImageView = itemView.findViewById(R.id.img_photo2)
    }
}