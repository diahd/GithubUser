package com.example.consumerapp.favorite

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import de.hdodenhof.circleimageview.CircleImageView

class FavUserAdapter(private var activity: Activity): RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {
    var listFav = ArrayList<FavUsers>()
        set(listFav) {
            if (listFav.size > 0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)

            notifyDataSetChanged()
        }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserAdapter.ListViewHolder {
        val view: View = LayoutInflater.from (parent.context).inflate(R.layout.item_fav, parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listFav.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        val user = listFav[position]

        Glide.with(holder.itemView.context)
            .load(user.photo)
            .apply(RequestOptions().override(55,55))
            .into(holder.imgAva)

        holder.txtUname.text = user.user
        holder.txtCom.text = user.company
        holder.txtLoc.text = user.loc

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFav[holder.adapterPosition])
        }
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtUname: TextView = itemView.findViewById(R.id.txt_uname)
        val txtLoc: TextView = itemView.findViewById(R.id.txt_location)
        val txtCom: TextView = itemView.findViewById(R.id.txt_company)
        val imgAva: CircleImageView = itemView.findViewById(R.id.img_ava)

    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavUsers)
    }
}