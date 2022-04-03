package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.harshad.projectclean.APIRequests.authentication_data_class.AuthenticationFunctions
import com.harshad.projectclean.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)
        var isRemembered = sharedPref.getBoolean("REMEMBER", false)

        if (isRemembered){
            val intent = Intent(this, DisplayGrievance::class.java)
            startActivity(intent)
            finish()
        }

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, ProfileSetup::class.java)
            startActivity(intent)
            finish()
        }

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)


        binding.btnLogin.setOnClickListener {
            if (binding.edEmail.text.trim().isNotEmpty() || binding.edPassword.text.trim()
                    .isNotEmpty()
            ) {
                val email: String = binding.edEmail.text.trim().toString()
                val password: String = binding.edPassword.text.trim().toString()
                validate()
                val loginObj = AuthenticationFunctions(this,sharedPref)
                Log.d("Logging","Inside Loggin $email ")
                Toast.makeText(this,"Logging...",Toast.LENGTH_LONG).show()
                loginObj.login( email, password)

            } else {
                Toast.makeText(this, "Enter Info", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validate(): Boolean {
        if (binding.edEmail.text.trim().isNotEmpty() || binding.edPassword.text.trim()
                .isNotEmpty()
        ) {
            return true
        }
        return true

    }
}