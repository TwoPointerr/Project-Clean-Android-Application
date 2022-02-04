package com.harshad.projectclean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.GrievancesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_display_gri = findViewById<Button>(R.id.btn_display_gri)

        btn_display_gri.setOnClickListener {
            getGriData()
            Toast.makeText(this, "You clicked me.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getGriData(){
        var apiClient: ApiClient = ApiClient()
        apiClient.getApiService().getGrievanceData(token = "Token f1fda758da079242d64a1c2c8c942c222d1e8707")
            .enqueue(object:Callback<GrievancesListResponse>{
            override fun onResponse(
                call: Call<GrievancesListResponse>,
                response: Response<GrievancesListResponse>
            ) {
                var gri_resposne = response.body()
                Log.d("Gri Data","Response Success ${gri_resposne}")
            }

            override fun onFailure(call: Call<GrievancesListResponse>, t: Throwable) {
                Log.d("Gri Data","Response ERROR ${t} ")
            }
        })
    }
}