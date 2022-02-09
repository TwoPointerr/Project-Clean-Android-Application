package com.harshad.projectclean

import android.content.Intent
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
            var intent = Intent(this, DisplayGrievance::class.java)
            Toast.makeText(this, "You clicked me.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }


}