package com.harshad.projectclean.APIRequests

import com.harshad.projectclean.APIRequests.authentication_data_class.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationRequests {
    @POST(URLConstants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST
    fun registerUser(@Body request:RegisterRequest):Call<RegisterResponse>

    @GET(URLConstants.GET_LOGGED_USER)
    fun getCurrentLoggedUser(@Header("Authorization") token:String):Call<CurrentLoggedUserResponse>
}