package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.authentication_data_class.AuthenticationFunctions
import com.harshad.projectclean.APIRequests.authentication_data_class.RegisterRequest
import com.harshad.projectclean.APIRequests.authentication_data_class.RegisterResponse
import com.harshad.projectclean.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var sharedPref : SharedPreferences

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.textLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)


        binding.btnSignup.setOnClickListener{
            if(validate()){
                val email:String = binding.edEmail.text.toString()
                val username:String = binding.edUsername.text.toString()
                val password:String = binding.edPassword.text.toString()
                val editor : SharedPreferences.Editor =sharedPref.edit()
                editor.putString("username", username)
                editor.putString("email", email)
                editor.putString("password", password)

                editor.apply()
                Toast.makeText(this,"Profile", Toast.LENGTH_LONG).show()
//                val authenticationFunctions = AuthenticationFunctions(this,sharedPref)
//                authenticationFunctions.register(
//                    username=username,
//                    email=email,
//                    password=password
//                )
                val intent = Intent(this, ProfileSetup::class.java)
//                intent.putExtra("Username",username)
//                intent.putExtra("Phoneno",phoneno)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Enter Info", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun validate(): Boolean {
        return (binding.edUsername.text.trim().isNotEmpty() )
    }
}