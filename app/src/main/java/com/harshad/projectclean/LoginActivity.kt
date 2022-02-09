package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.harshad.projectclean.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPref : SharedPreferences
    private var isRemembered =false
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)


        binding.btnLogin.setOnClickListener{
            if(binding.edUsername.text.trim().isNotEmpty() || binding.edPassword.text.trim().isNotEmpty()){
                val username:String = binding.edUsername.text.toString()
                val password:String = binding.edPassword.text.toString()

                val editor : SharedPreferences.Editor =sharedPref.edit()
                editor.putString("username", username)
                editor.putString("pass", password)
                editor.putBoolean("REMEMBER", true)

                editor.apply()

                Toast.makeText(this,"Remember",Toast.LENGTH_LONG).show()



                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Enter Info",Toast.LENGTH_LONG).show()
            }
        }
    }
}