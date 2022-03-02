package com.harshad.projectclean.APIRequests

import android.location.LocationRequest
import com.harshad.projectclean.APIRequests.grievance_data_class.GrievanceLocationRequest
import com.harshad.projectclean.APIRequests.grievance_data_class.GrievancesListResponse
import com.harshad.projectclean.APIRequests.grievance_data_class.TemporaryGriResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GrievanceRequests {
    @GET(URLConstants.GET_GRI_DATA)
    fun getGrievanceData(@Header("Authorization") token:String):Call<GrievancesListResponse>

    @Multipart
    @POST(URLConstants.UPLOAD_GRI)
    fun createGrievance(@Part gri_img: MultipartBody.Part,
                        @Header("Authorization") token:String,
                        @Part("gri_title") gri_title: RequestBody?,
                        @Part("gri_desc") gri_desc:RequestBody?,
                        @Part("gri_category") gri_category:RequestBody?,
                        @Part("gri_uploaded_user") gri_uploaded_user:RequestBody?,
                        @Part("loc_lat") loc_lat:RequestBody?,
                        @Part("loc_long") loc_long:RequestBody?,
    ):Call<TemporaryGriResponse>
}