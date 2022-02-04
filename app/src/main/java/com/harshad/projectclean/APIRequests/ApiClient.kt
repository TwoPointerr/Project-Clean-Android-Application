package com.harshad.projectclean.APIRequests

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var grievanceRequests: GrievanceRequests
    fun getApiService(): GrievanceRequests{
        if(!::grievanceRequests.isInitialized){
            var retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            grievanceRequests = retrofit.create(GrievanceRequests::class.java)
        }
        return grievanceRequests
    }
}