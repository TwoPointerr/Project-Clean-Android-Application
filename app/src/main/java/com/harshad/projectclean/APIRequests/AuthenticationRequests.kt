package com.harshad.projectclean.APIRequests

import com.harshad.projectclean.APIRequests.authentication_data_class.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AuthenticationRequests {
    @POST(URLConstants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(URLConstants.REGISTER_URL)
    fun registerUser(@Body request:RegisterRequest):Call<RegisterResponse>

    @Multipart
    @POST(URLConstants.PROFILE_UPDATE_CREATE)
    fun profileUpdateCreate(
        @Part profile_img: MultipartBody.Part,
        @Header("Authorization") token:String,
        @Part("id") id: RequestBody?,
        @Part("username") username: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("first_name") first_name: RequestBody?,
        @Part("last_name") last_name: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("phone") phone: RequestBody?,
    ):Call<ProfileUpdateResponse>

    @Multipart
    @POST(URLConstants.PROFILE_CREATE)
    fun profileCreate(
        @Part citi_profile_img: MultipartBody.Part,
        @Part("citi_user") user_id: RequestBody?,
    ):Call<ProfileCreateResponse>



    @GET(URLConstants.GET_LOGGED_USER)
    fun getCurrentLoggedUser(@Header("Authorization") token:String):Call<CurrentLoggedUserResponse>
}