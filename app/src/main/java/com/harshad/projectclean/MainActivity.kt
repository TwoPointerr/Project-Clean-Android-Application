package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.harshad.projectclean.databinding.ActivityMainBinding

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

        binding.tvDemoText.text = sharedPref.getString("auth_token","No auth token").toString()

        binding.btnDisplayGri.setOnClickListener {
            val intent = Intent(this, DisplayGrievance::class.java)
            startActivity(intent)
            finish()
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




        isRemembered = sharedPref.getBoolean("REMEMBER", false)

        if (!isRemembered){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            binding.btnLogout.setVisibility(View.VISIBLE)
            binding.btnLogin.setVisibility(View.INVISIBLE)
        }



        binding.btnLogout.setOnClickListener{

            val editor :SharedPreferences.Editor =sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}