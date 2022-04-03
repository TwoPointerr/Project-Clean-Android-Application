package com.harshad.projectclean

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.net.toFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.authentication_data_class.AuthenticationFunctions
import com.harshad.projectclean.databinding.ActivityProfileSetupBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProfileSetup : AppCompatActivity() {

    lateinit var sharedPref : SharedPreferences
    lateinit var profileImg : File

    private lateinit var binding: ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)

        val authenticationFunctions = AuthenticationFunctions(this,sharedPref)
        val token = sharedPref.getString("auth_token","")
        binding.edFirstname.requestFocus()
        binding.btnSave.setOnClickListener {
            Log.d("Profile setup","token ${token}")
            val first_name = binding.edFirstname.text.trim().toString()
            val last_name = binding.edLastname.text.trim().toString()
            val email = binding.edEmail.text.trim().toString()
            val username = binding.edUsername.text.trim().toString()
            val phone = binding.edPhoneno.text.trim().toString()
            val password = binding.edPassword.text.trim().toString()

            val authenticationFunctions = AuthenticationFunctions(this,sharedPref)
            if(this::profileImg.isInitialized){
                Toast.makeText(this, "Registering account...", Toast.LENGTH_LONG).show()
                authenticationFunctions.register(
                    username=username,
                    email=email,
                    password=password,
                    first_name=first_name,
                    last_name=last_name,
                    profile_img = profileImg
                )
            }else{
                Toast.makeText(this, "Please choose profile image.", Toast.LENGTH_LONG).show()
            }

//            if(token != null){
//                if (this::profileImg.isInitialized) {
//                    sharedPref.getString("auth_token","")?.let { it1 ->
//                        authenticationFunctions.profileUpdateCreate(
//                            id = sharedPref.getInt("user_id",0).toString(),
//                            token = it1,
//                            profile_img = profileImg,
//                            first_name = first_name,
//                            last_name = last_name,
//                            email = email,
//                            username = username,
//                            phone = phone,
//                            password = password
//                        )
//                    }
//                }else{
//                    Toast.makeText(
//                        this,
//                        "Wait for to Image load",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }else{
//                Toast.makeText(
//                    this,
//                    "Please Login",
//                    Toast.LENGTH_LONG
//                ).show()
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        }
        binding.fabImg.setOnClickListener {
            ImagePicker.with(this).galleryMimeTypes(arrayOf("image/*"))
                .maxResultSize(128, 128).saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            ).start()
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {


            binding.profileImage.setImageURI(data?.data)
            Log.d("Asach","${data?.data}")

            profileImg = data?.data?.toFile()!!


        }

    }
}