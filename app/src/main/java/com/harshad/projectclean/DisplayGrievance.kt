package com.harshad.projectclean

import android.content.Context
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.*
import com.harshad.projectclean.grievance_recycler_view_classes.GrievanceRecycleViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayGrievance : AppCompatActivity() {
    lateinit var adapter: GrievanceRecycleViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_grievance)
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

                    val gri_resposne = response.body()
                    var recy_grievance_list = findViewById<RecyclerView>(R.id.recy_grievance_list)
                    recy_grievance_list.adapter = GrievanceRecycleViewAdapter(context,gri_resposne!!.gri_data)
                    recy_grievance_list.layoutManager = LinearLayoutManager(context)
                    Log.d("Gri Data","Response Success ${gri_resposne.gri_data}")
                }

                override fun onFailure(call: Call<GrievancesListResponse>, t: Throwable) {
                    Log.d("Gri Data","Response ERROR ${t} ")
                }
            })
    }
}