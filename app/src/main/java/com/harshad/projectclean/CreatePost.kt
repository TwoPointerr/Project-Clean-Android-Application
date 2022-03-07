package com.harshad.projectclean

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.grievance_data_class.TemporaryGriResponse
import com.harshad.projectclean.databinding.ActivityCreatePostBinding
import okhttp3.MediaType.Companion.toMediaType
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception


class CreatePost : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    val items = arrayOf("Garbage", "Pothole", "Fallen Tree", "Other")
    lateinit var actv :AutoCompleteTextView
    lateinit var  adapterItems : ArrayAdapter<String>
    lateinit var imgFile:File
    lateinit var sharedPref:SharedPreferences
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ImagePicker.with(this).cameraOnly()
            .compress(512)
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!).start()

        binding.fabImg.setOnClickListener {
            ImagePicker.with(this).cameraOnly()
                .compress(512)
                .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!).start()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)

        actv = binding.dropMenu
        adapterItems = ArrayAdapter<String>(this,R.layout.list_item,items)
        actv.setAdapter(adapterItems)
        actv.setOnItemClickListener(OnItemClickListener { parent, _, position, _ ->  //parent,view, position, id
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
        })



        binding.btnSave.setOnClickListener {
            val griTitle = binding.edTitle.text.trim().toString()
            val griDesc = binding.edDesc.text.trim().toString()
            Log.d("Create Post","Gri Title $griTitle")

            create_gri(
                imgFile=imgFile,
                gri_title = griTitle,
                gri_desc = griDesc,
                gri_uploaded_user = sharedPref.getInt("user_id",0).toString(),
                gri_loc_lat = "19.066541",
                gri_loc_long = "72.998431",
                gri_category = "2",
                context = this
            )

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {



            binding.ivGriImg.setImageURI(data?.data)

            Log.d("ImgFile3","${data?.data}")
            imgFile = data?.data?.toFile()!!


        }


    }
    private fun fetchLocation(){
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

                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}",Toast.LENGTH_LONG).show()
                lateinit var add : String
                try {
                    add = getAddress(it.latitude, it.longitude)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
                binding.tvLocation.setText(add)

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

    private fun create_gri(imgFile: File,
                           gri_title:String,
                           gri_desc:String,
                           gri_category:String,
                           gri_uploaded_user:String,
                           gri_loc_lat:String,
                           gri_loc_long:String,
                           context: Context
    )
    {
        val apiClient = ApiClient()

        val requestImageFile = imgFile.asRequestBody("image/*".toMediaType())
        val multiPartFormBody: MultipartBody.Part = MultipartBody.Part.createFormData("gri_img","IMG-20220227-WA0000.jpg",requestImageFile)

        apiClient.grievanceApiRequests().createGrievance(
            multiPartFormBody,
            token = "Token ${sharedPref.getString("auth_token","")}",
            gri_title = gri_title.toRequestBody(),
            gri_desc = gri_desc.toRequestBody(),
            gri_category = gri_category.toRequestBody(),
            gri_uploaded_user = gri_uploaded_user.toRequestBody(),
            loc_long = gri_loc_long.toRequestBody(),
            loc_lat = gri_loc_lat.toRequestBody()
        ).enqueue(object: Callback<TemporaryGriResponse> {
            override fun onResponse(
                call: Call<TemporaryGriResponse>,
                response: Response<TemporaryGriResponse>
            ) {
                Log.d("Gri Create","Gri Create Successful ${response}")
                val intent = Intent(context, DisplayGrievance::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<TemporaryGriResponse>, t: Throwable) {
                Log.d("Gri Create","Gri Create UN-Successful ${t}")
            }
        })
    }
}