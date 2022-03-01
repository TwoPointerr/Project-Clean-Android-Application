package com.harshad.projectclean

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.APIRequests.authentication_data_class.CurrentLoggedUserResponse
import com.harshad.projectclean.APIRequests.authentication_data_class.LoginRequest
import com.harshad.projectclean.APIRequests.authentication_data_class.LoginResponse
import com.harshad.projectclean.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)


        binding.btnLogin.setOnClickListener {
            if (binding.edUsername.text.trim().isNotEmpty() || binding.edPassword.text.trim()
                    .isNotEmpty()
            ) {
                val username: String = binding.edUsername.text.trim().toString()
                val password: String = binding.edPassword.text.trim().toString()
                validate()
                login(this, username, password)
                //Toast.makeText(this,"Remember",Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Enter Info", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login(context: Context, email: String, password: String) {
        val apiClient: ApiClient = ApiClient()
        val editor: SharedPreferences.Editor = sharedPref.edit()
        var sharedPref = getSharedPreferences("SP", Context.MODE_PRIVATE)

        apiClient.authenticationApiRequests()
            .login(LoginRequest(email = email, password = password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (loginResponse?.auth_token != null) {
                        editor.putString("auth_token", loginResponse.auth_token)
                        editor.apply()
                        Toast.makeText(
                            context,
                            "Successful Login ${loginResponse.auth_token}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_LONG).show()
                }
            })
    }

    fun storeCurrentLoggedUser(token:String){
        val apiClient: ApiClient = ApiClient()
        val editor: SharedPreferences.Editor = sharedPref.edit()

        apiClient.authenticationApiRequests()
            .getCurrentLoggedUser(token = "Token $token")
            .enqueue(object :Callback<CurrentLoggedUserResponse>{
                override fun onResponse(
                    call: Call<CurrentLoggedUserResponse>,
                    response: Response<CurrentLoggedUserResponse>
                ) {
                    val currentUser = response.body()
                    editor.putInt("user_id", currentUser!!.id)
                    editor.putString("user_first_name", currentUser.first_name)
                    editor.putString("user_last_name", currentUser.last_name)
                    editor.putString("user_email", currentUser.email)
                    editor.putString("user_username", currentUser.username)
                    editor.apply()
                    Log.d("Current User","Info ${sharedPref.getString("user_first_name","No name")} ${sharedPref.getInt("user_id",0).toString()}")
                    //Log.d("Current User","Successful ${response.body()}")
                }
                override fun onFailure(call: Call<CurrentLoggedUserResponse>, t: Throwable) {
                    Log.d("Current User","Unsuccessful $t")
                }
            })
    }
    private fun validate(): Boolean {
        if (binding.edUsername.text.trim().isNotEmpty() || binding.edPassword.text.trim()
                .isNotEmpty()
        ) {
            return true
        }
        return true

    }
}