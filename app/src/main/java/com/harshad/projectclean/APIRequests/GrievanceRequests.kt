package com.harshad.projectclean.APIRequests

import com.harshad.projectclean.APIRequests.grievance_data_class.GrievancesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GrievanceRequests {
    @GET(URLConstants.GET_GRI_DATA)
    fun getGrievanceData(@Header("Authorization") token:String):Call<GrievancesListResponse>
}