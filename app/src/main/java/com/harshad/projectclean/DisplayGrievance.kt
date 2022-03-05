package com.harshad.projectclean

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.GrievancesListResponse
import com.harshad.projectclean.databinding.ActivityDisplayGrievanceBinding
import com.harshad.projectclean.grievance_recycler_view_classes.GrievanceRecycleViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayGrievance : AppCompatActivity() {
    private lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var binding: ActivityDisplayGrievanceBinding
    lateinit var adapter: GrievanceRecycleViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayGrievanceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mShimmerViewContainer =binding.shimmerLayout
        getGriData(this)

    }

    fun getGriData(context:Context){
        var apiClient: ApiClient = ApiClient()
        var sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
        apiClient.grievanceApiRequests().getGrievanceData(token = "Token ${sharedPref.getString("auth_token","")}")
            .enqueue(object: Callback<GrievancesListResponse> {
                override fun onResponse(
                    call: Call<GrievancesListResponse>,
                    response: Response<GrievancesListResponse>
                ) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            val gri_resposne = response.body()
                            var recy_grievance_list =
                                findViewById<RecyclerView>(R.id.recy_grievance_list)
                            recy_grievance_list.adapter =
                                GrievanceRecycleViewAdapter(context, gri_resposne!!.gri_data)
                            recy_grievance_list.layoutManager = LinearLayoutManager(context)
                            Log.d("Gri Data", "Response Success ${gri_resposne.gri_data}")
                        }
                        mShimmerViewContainer.stopShimmer()
                        mShimmerViewContainer.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<GrievancesListResponse>, t: Throwable) {
                    Log.d("Gri Data","Response ERROR ${t} ")
                }
            })
    }
    override fun onResume() {
        super.onResume()
        mShimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        mShimmerViewContainer.stopShimmer()
        super.onPause()
    }
}