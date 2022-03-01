package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.*
import com.harshad.projectclean.databinding.ActivityMainBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var sharedPref : SharedPreferences
    private var isRemembered = false

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnLogout.setVisibility(View.INVISIBLE)

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
        binding.btnDisplayGri.setOnClickListener {
            val intent = Intent(this, DisplayGrievance::class.java)
            startActivity(intent)
            finish()
            //create_gri()
        }

        binding.fabNew.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnProfileSetup.setOnClickListener {
            val intent = Intent(this, ProfileSetup::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //isRemembered = sharedPref.getBoolean("REMEMBER", false)

//        if (!isRemembered){
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        else{
//
//            binding.btnLogin.setVisibility(View.INVISIBLE)
//        }
        binding.btnLogout.setVisibility(View.VISIBLE)
        binding.btnLogout.setOnClickListener{

            val editor :SharedPreferences.Editor =sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvDemoText.text = sharedPref.getString("auth_token","No auth token").toString()

    }

    private fun create_gri() {
        val apiClient = ApiClient()
        //val IMG_PATH = "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/"
        //val imageFile = File(IMG_PATH)

        //val requestImageFile = imageFile.asRequestBody("image/*".toMediaType())
        val gri_title = "Uploaded from android".toRequestBody()
        val gri_desc = "Some android desc".toRequestBody()
        val gri_category = "Pothole".toRequestBody()
        val gri_uploaded_user = "2".toRequestBody()
        val gri_upvote = "500".toRequestBody()
        val gri_loc_lat = "19.066541".toRequestBody()
        val gri_loc_long = "72.998431".toRequestBody()

        //val multiPartFormBody: MultipartBody.Part = MultipartBody.Part.createFormData("gri_img","IMG-20220227-WA0000.jpg",requestImageFile)
        apiClient.grievanceApiRequests().createGrievance(
            //multiPartFormBody,
            gri_title = gri_title,
            gri_desc = gri_desc,
            gri_category = gri_category,
            gri_uploaded_user = gri_uploaded_user,
            gri_upvote = gri_upvote,
            loc_long = gri_loc_long,
            loc_lat = gri_loc_lat
        ).enqueue(object:Callback<TemporaryGriResponse>{
            override fun onResponse(
                call: Call<TemporaryGriResponse>,
                response: Response<TemporaryGriResponse>
            ) {
                Log.d("Gri Create","Gri Create Successful ${response}")
            }

            override fun onFailure(call: Call<TemporaryGriResponse>, t: Throwable) {
                Log.d("Gri Create","Gri Create UN-Successful ${t}")
            }
        })
    }
}