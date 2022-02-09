package com.harshad.projectclean.grievance_recycler_view_classes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshad.projectclean.APIRequests.URLConstants
import com.harshad.projectclean.APIRequests.grievance_data_class.SingleGrievance
import com.harshad.projectclean.R


class GrievanceRecycleViewAdapter(private val context: Context, private val grievance_list:List<SingleGrievance>):RecyclerView.Adapter<GrievanceRecycleViewAdapter.GrievanceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrievanceViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.single_grievance_item_view,parent,false)
        return GrievanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrievanceViewHolder, position: Int) {
        Glide.with(context).load("${URLConstants.BASE_URL}${grievance_list[position].gri_img}").into(holder.gri_img)
        holder.gri_title.text = grievance_list[position].gri_title
        holder.gri_desc.text = grievance_list[position].gri_desc

    }

    override fun getItemCount(): Int {
        return grievance_list.size
    }

    class GrievanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var gri_title = itemView.findViewById<TextView>(R.id.tv_gri_title)
        var gri_location = itemView.findViewById<TextView>(R.id.tv_gri_location)
        var gri_desc = itemView.findViewById<TextView>(R.id.tv_gri_desc)
        var gri_img = itemView.findViewById<ImageView>(R.id.iv_gri_img)
    }

}

