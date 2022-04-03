package com.harshad.projectclean.grievance_recycler_view_classes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.URLConstants
import com.harshad.projectclean.APIRequests.grievance_data_class.SingleGrievanceResponse
import com.harshad.projectclean.APIRequests.grievance_data_class.UpvoteRequest
import com.harshad.projectclean.APIRequests.grievance_data_class.UpvoteResponse
import com.harshad.projectclean.R
import com.theophrast.ui.widget.SquareImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GrievanceRecycleViewAdapter(private val context: Context,
                                  private val grievance_Response_list:List<SingleGrievanceResponse>
                                  ):RecyclerView.Adapter<GrievanceRecycleViewAdapter.GrievanceViewHolder>() {
    var sharedPref = context.getSharedPreferences("SP", Context.MODE_PRIVATE)
    var apiClient: ApiClient = ApiClient()
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
        holder.gri_user.text = "${grievance_Response_list[position].gri_uploaded_user.citi_user.first_name} ${grievance_Response_list[position].gri_uploaded_user.citi_user.last_name}"
        holder.gri_location.text ="${grievance_Response_list[position].gri_location.loc_suburb}, ${grievance_Response_list[position].gri_location.loc_city}, ${grievance_Response_list[position].gri_location.loc_postcode}"
        holder.gri_timestamp.text = grievance_Response_list[position].gri_timeStamp
        holder.gri_upvote.text = grievance_Response_list[position].gri_upvote.toString()

        Log.d("Display Gri","Gri Location ${grievance_Response_list[position]}")
        //holder.gri_rank.text= grievance_Response_list[position].gri_priority.toString()
        holder.gri_like_btn.setOnClickListener {
            Toast.makeText(context,"Registering Upvote...",Toast.LENGTH_LONG).show()
            upvoteGri(gri_id = grievance_Response_list[position].id,gri_upvote_textView = holder.gri_upvote)
            Log.d("Gri Recycler View","Inside on bind viewHolder ${grievance_Response_list[position].id} ${grievance_Response_list[position].gri_title} ${sharedPref.getString("user_first_name","No name")}")
        }



    }

    override fun getItemCount(): Int {
        return grievance_Response_list.size
    }

    class GrievanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var gri_user = itemView.findViewById<TextView>(R.id.textVUsername)
        var gri_title = itemView.findViewById<TextView>(R.id.textVtitle)
        //var gri_rank = itemView.findViewById<TextView>(R.id.textVRank)
        var gri_cat = itemView.findViewById<TextView>(R.id.textVCat)
        var gri_location = itemView.findViewById<TextView>(R.id.textVlocation)
        var gri_desc = itemView.findViewById<TextView>(R.id.textVDesc)
        var gri_img = itemView.findViewById<SquareImageView>(R.id.post_image)
        var gri_like_btn = itemView.findViewById<ImageView>(R.id.icon_up)
        var gri_comment_btn = itemView.findViewById<ImageView>(R.id.iconComments)
        var gri_timestamp = itemView.findViewById<TextView>(R.id.textVTimePosted)
        var gri_status = itemView.findViewById<TextView>(R.id.textVStatus)
        var gri_upvote = itemView.findViewById<TextView>(R.id.textVUpvote)
    }
    fun upvoteGri(gri_id:Int,gri_upvote_textView:TextView) {
        apiClient.grievanceApiRequests().upvoteGrievance(
            token = "Token ${sharedPref.getString("auth_token","")}",
            UpvoteRequest(
                user_id = sharedPref.getInt("user_id",0),
                gri_id = gri_id
            )
        ).enqueue(object : Callback<UpvoteResponse> {
            override fun onResponse(
                call: Call<UpvoteResponse>,
                response: Response<UpvoteResponse>
            ) {
                gri_upvote_textView.text = response.body()?.gri_upvotes.toString()
                Toast.makeText(context,"Every Upvote count, Thank you",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<UpvoteResponse>, t: Throwable) {
                Toast.makeText(context,"Something get , Upvote didn't get count",Toast.LENGTH_LONG).show()

            }
        })

    }

}


