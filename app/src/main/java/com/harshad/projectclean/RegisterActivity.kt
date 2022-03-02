package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.harshad.projectclean.databinding.ActivityRegisterBinding

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
                //val phoneno:String = binding.edPhoneno.text.toString()
                val username:String = binding.edUsername.text.toString()
                val password:String = binding.edPassword.text.toString()
                val editor : SharedPreferences.Editor =sharedPref.edit()
                editor.putString("username", username)
                //editor.putString("phoneno", phoneno)

                editor.apply()
                Toast.makeText(this,"Profile", Toast.LENGTH_LONG).show()

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