package com.harshad.projectclean.APIRequests.authentication_data_class

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.harshad.projectclean.APIRequests.ApiClient
import com.harshad.projectclean.DisplayGrievance
import com.harshad.projectclean.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AuthenticationFunctions(context: Context, sharedPreferences: SharedPreferences) {
    val context:Context = context
    val apiClient: ApiClient = ApiClient()
    var sharedPref = sharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun login(email: String, password: String) {
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
                        editor.putBoolean("REMEMBER", true)
                        editor.apply()

                        storeCurrentLoggedUser(loginResponse.auth_token)
                        Log.d("Logging","Successful Login $loginResponse")
                        Toast.makeText(
                            context,
                            "Successful Logged In",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(context, DisplayGrievance::class.java)
                        val activity = context as Activity
                        activity.startActivity(intent)
                        activity.finish()
                    }else{
                        Log.d("Logging","Token is null")
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Logging","Logging Unsuccessful")
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            })
    }

    fun register(username:String,
                 email:String,
                 password:String,
                 first_name: String,
                 last_name: String,
                 profile_img: File
    ){
        apiClient
            .authenticationApiRequests()
            .registerUser(RegisterRequest(
                username=username,
                email = email,
                password = password,
                re_password = password,
                first_name = first_name,
                last_name = last_name,
            )).enqueue(object :Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val registerResponse = response.body()
                    Toast.makeText(
                        context,
                        "Account created successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(
                        context,
                        "Please Wait.., Setting up Profile.",
                        Toast.LENGTH_LONG
                    ).show()
                    profileCreate(
                        id= response.body()?.id.toString(),
                        profile_img = profile_img
                    )
                    Toast.makeText(
                        context,
                        "Please Wait..",
                        Toast.LENGTH_LONG
                    ).show()
                    login(email, password)
                    Log.d("Register","User Created ${registerResponse?.email}")

                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Sorry, Couldn't create account, Try again",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Register","User Created Un successful ${t}")
                }
            })
    }

    fun profileCreate(
        id:String,
        profile_img:File,
    ){
        Log.d("Profile Setup","Before Response File:${profile_img} File name${profile_img.name}")
        val requestImageFile = profile_img.asRequestBody("image/*".toMediaType())
        val multiPartFormBody: MultipartBody.Part = MultipartBody.Part.createFormData("profile_img",profile_img.name,requestImageFile)
        apiClient.authenticationApiRequests()
            .profileCreate(
                citi_profile_img = multiPartFormBody,
                user_id = id.toRequestBody()
            ).enqueue(object :Callback<ProfileCreateResponse>{
                override fun onResponse(
                    call: Call<ProfileCreateResponse>,
                    response: Response<ProfileCreateResponse>
                ) {
                    Toast.makeText(
                        context,
                        "Profile Setup Complete",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Profile Setup","Successful ${response.body()}")
                }
                override fun onFailure(call: Call<ProfileCreateResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Something went wrong, Profile set not complete",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Profile Setup","UN-Successful ${t}")
                }
            })

    }

    fun profileUpdateCreate(
        token: String,
        id:String,
        profile_img:File,
        first_name:String,
        last_name:String,
        email:String,
        username:String,
        password: String,
        phone:String,
    ){
        Log.d("Profile Setup","Before Response File:${profile_img} File name${profile_img.name}")
        val requestImageFile = profile_img.asRequestBody("image/*".toMediaType())
        val multiPartFormBody: MultipartBody.Part = MultipartBody.Part.createFormData("profile_img",profile_img.name,requestImageFile)
        if (token != null) {
            apiClient.authenticationApiRequests().profileUpdateCreate(
                    profile_img = multiPartFormBody,
                    token = "Token $token",
                    id = id.toRequestBody(),
                    first_name = first_name.toRequestBody(),
                    last_name = last_name.toRequestBody(),
                    email = email.toRequestBody(),
                    username = username.toRequestBody(),
                    password = password.toRequestBody(),
                    phone = phone.toRequestBody()
                ).enqueue(object:Callback<ProfileUpdateResponse>{
                override fun onResponse(
                    call: Call<ProfileUpdateResponse>,
                    response: Response<ProfileUpdateResponse>
                ) {
                    response.body()?.email?.let {
                        response.body()?.id?.let { it1 ->
                            response.body()?.username?.let { it2 ->
                                response.body()?.first_name?.let { it3 ->
                                    response.body()?.last_name?.let { it4 ->
                                        response.body()?.phone?.let { it5 ->
                                            storeUserSharedPref(
                                                user_id  = it1,
                                                email = it,
                                                username = it2,
                                                first_name = it3,
                                                last_name = it4,
                                                phone = it5
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Toast.makeText(
                        context,
                        "Profile Setup Complete ${response.body()?.first_name} ${response.body()?.last_name}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Profile Setup","Successful ${response.body()}")

                }

                override fun onFailure(call: Call<ProfileUpdateResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Something Went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Profile Setup","Successful ${t}")
                }
            })


        }
    }

    fun storeUserSharedPref(
        user_id:Int,
        username: String,
        email: String,
        first_name: String,
        last_name: String,
        phone: String
    ){
        editor.putInt("user_id", user_id)
        editor.putString("user_first_name", first_name)
        editor.putString("user_last_name", last_name)
        editor.putString("user_email", email)
        editor.putString("user_username", username)
        editor.putString("user_phone", phone)
        editor.apply()
    }

    fun storeCurrentLoggedUser(token:String){
        apiClient.authenticationApiRequests()
            .getCurrentLoggedUser(token = "Token $token")
            .enqueue(object :Callback<CurrentLoggedUserResponse>{
                override fun onResponse(
                    call: Call<CurrentLoggedUserResponse>,
                    response: Response<CurrentLoggedUserResponse>
                ) {
                    val currentUser = response.body()
                    storeUserSharedPref(
                        user_id = currentUser!!.id,
                        first_name = currentUser.first_name,
                        last_name = currentUser.last_name,
                        email = currentUser.email,
                        username = currentUser.username,
                        phone = ""
                    )
                    Toast.makeText(
                        context,
                        "Welcome, ${sharedPref.getString("user_first_name","No name")} ${sharedPref.getString("user_last_name","No name")}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Current User","Info ${sharedPref.getString("user_first_name","No name")} ${sharedPref.getInt("user_id",0).toString()}")
                    //Log.d("Current User","Successful ${response.body()}")

                }
                override fun onFailure(call: Call<CurrentLoggedUserResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Something went wrong, cannot store users credential",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Current User","Unsuccessful $t")
                }
            })
    }


}