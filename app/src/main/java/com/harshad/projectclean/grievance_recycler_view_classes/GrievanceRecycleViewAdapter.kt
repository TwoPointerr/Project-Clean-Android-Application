package com.harshad.projectclean.grievance_recycler_view_classes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshad.projectclean.APIRequests.URLConstants
import com.harshad.projectclean.APIRequests.grievance_data_class.SingleGrievanceResponse
import com.harshad.projectclean.R
import com.theophrast.ui.widget.SquareImageView


class GrievanceRecycleViewAdapter(private val context: Context, private val grievance_Response_list:List<SingleGrievanceResponse>):RecyclerView.Adapter<GrievanceRecycleViewAdapter.GrievanceViewHolder>() {
    var sharedPref = context.getSharedPreferences("SP", Context.MODE_PRIVATE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrievanceViewHolder {

        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.single_grievance_item_view,parent,false)
        return GrievanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrievanceViewHolder, position: Int) {
        Glide.with(context).load("${URLConstants.BASE_URL}${grievance_Response_list[position].gri_img}").into(holder.gri_img)
        holder.gri_title.text = grievance_Response_list[position].gri_title
        holder.gri_cat.text = grievance_Response_list[position].gri_category.cat_name
        holder.gri_desc.text = grievance_Response_list[position].gri_desc
        holder.gri_user.text = grievance_Response_list[position].gri_uploaded_user.citi_user.username
//        holder.gri_location.text =grievance_Response_list[position].gri_locationResponse.loc_display_name
        holder.gri_rank.text= grievance_Response_list[position].gri_priority.toString()
        holder.gri_like_btn.setOnClickListener {
            Log.d("Gri Recycler View","Inside on bind viewHolder ${grievance_Response_list[position].id} ${grievance_Response_list[position].gri_title} ${sharedPref.getString("user_first_name","No name")}")
        }

    }

    override fun getItemCount(): Int {
        return grievance_Response_list.size
    }

    class GrievanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var gri_user = itemView.findViewById<TextView>(R.id.textVUsername)
        var gri_title = itemView.findViewById<TextView>(R.id.textVtitle)
        var gri_rank = itemView.findViewById<TextView>(R.id.textVRank)
        var gri_cat = itemView.findViewById<TextView>(R.id.textVCat)
        var gri_location = itemView.findViewById<TextView>(R.id.textVlocation)
        var gri_desc = itemView.findViewById<TextView>(R.id.textVDesc)
        var gri_img = itemView.findViewById<SquareImageView>(R.id.post_image)
        var gri_like_btn = itemView.findViewById<ImageView>(R.id.icon_up)
        var gri_comment_btn = itemView.findViewById<ImageView>(R.id.iconComments)
    }

}

