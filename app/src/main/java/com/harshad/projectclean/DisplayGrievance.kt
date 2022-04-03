package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.DisplayGrievanceLocationRequest
import com.harshad.projectclean.APIRequests.grievance_data_class.GrievancesListResponse
import com.harshad.projectclean.APIRequests.grievance_data_class.UpvoteRequest
import com.harshad.projectclean.APIRequests.grievance_data_class.UpvoteResponse
import com.harshad.projectclean.databinding.ActivityDisplayGrievanceBinding
import com.harshad.projectclean.grievance_recycler_view_classes.GrievanceRecycleViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayGrievance : AppCompatActivity() {
    private lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var binding: ActivityDisplayGrievanceBinding
    lateinit var adapter: GrievanceRecycleViewAdapter
    lateinit var loc_long:String
    lateinit var loc_lat:String
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var apiClient: ApiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        super.onCreate(savedInstanceState)
        binding = ActivityDisplayGrievanceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mShimmerViewContainer =binding.shimmerLayout
        fetchLocation(this)
        binding.fabNew.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivLogout.setOnClickListener {
            var sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor =sharedPref.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this,"Successfully Logged out",Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.tvLogout.setOnClickListener {
            var sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor =sharedPref.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this,"Successfully Logged out",Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun getGriData(context:Context,loc_long:Double,loc_lat:Double){
        var sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
        apiClient.grievanceApiRequests().getGrievanceData(
            token = "Token ${sharedPref.getString("auth_token","")}",
            DisplayLocationRequest = DisplayGrievanceLocationRequest(loc_lat = loc_lat, loc_long = loc_long)
        )
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
                    Toast.makeText(context,"Uhh Ohh!! Couldn't load data", Toast.LENGTH_LONG).show()
                    Log.d("Gri Data","Response ERROR ${t} ")
                }
            })
    }

    private fun fetchLocation(context: Context){
        val task = fusedLocationProviderClient.lastLocation
        Log.d("fetchLocation","In task listener")
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
            return
        }

        task.addOnSuccessListener {
            if (it!=null){
                loc_long = it.longitude.toString()
                loc_lat = it.latitude.toString()
                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}",Toast.LENGTH_LONG).show()
                getGriData(context, loc_lat = it.latitude, loc_long = it.longitude)
                lateinit var add : String
                try {
                    add = getAddress(it.latitude, it.longitude)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }

                Log.d("fetchLocation found",add)
                Toast.makeText(applicationContext,add,Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
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