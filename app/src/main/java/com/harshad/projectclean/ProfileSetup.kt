package com.harshad.projectclean

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.github.dhaval2404.imagepicker.ImagePicker
import com.harshad.projectclean.databinding.ActivityProfileSetupBinding

class ProfileSetup : AppCompatActivity() {

    lateinit var sharedPref : SharedPreferences

    private lateinit var binding: ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
        binding.edUsername.setText(sharedPref.getString("username","none"))
        binding.edPhoneno.setText(sharedPref.getString("phoneno","none"))
        binding.edFirstname.requestFocus()
        binding.btnSave.setOnClickListener {
            val intent = Intent(this, DisplayGrievance::class.java)
            startActivity(intent)
            finish()
        }
        binding.fabImg.setOnClickListener {
            ImagePicker.with(this).crop(1f, 1f).galleryMimeTypes(arrayOf("image/*"))
                .maxResultSize(720, 720).saveDir(
                getExternalFilesDir(
                    Environment.DIRECTORY_PICTURES
                )!!
            ).start()
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {


            binding.profileImage.setImageURI(data?.data)
            Log.d("Asach","${data?.data}")

            var s1:String
            s1 = data?.data.toString()
            Log.d("Bdach",s1)

        }

    }
}