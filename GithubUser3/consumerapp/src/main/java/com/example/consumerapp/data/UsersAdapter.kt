package com.example.consumerapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class UsersAdapter(private val listUser: ArrayList<Users>): RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from (parent.context).inflate(R.layout.item_users, parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        val user = listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.photo)
            .apply(RequestOptions().override(55,55))
            .into(holder.imgPhoto)

        holder.txtName.text = user.name
        holder.txtUser.text = user.user

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

   inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtUser: TextView = itemView.findViewById(R.id.txt_user)
        val imgPhoto: CircleImageView = itemView.findViewById(R.id.img_photo)

    }

    interface OnItemClickCallback{
        fun onItemClicked(data: Users)
    }
}

