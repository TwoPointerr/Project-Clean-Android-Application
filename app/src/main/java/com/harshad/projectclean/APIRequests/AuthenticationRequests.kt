package com.harshad.projectclean.APIRequests

import com.harshad.projectclean.APIRequests.authentication_data_class.LoginRequest
import com.harshad.projectclean.APIRequests.authentication_data_class.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationRequests {
    @POST(URLConstants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}