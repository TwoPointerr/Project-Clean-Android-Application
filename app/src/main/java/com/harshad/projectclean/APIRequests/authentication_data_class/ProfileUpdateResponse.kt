package com.harshad.projectclean.APIRequests.authentication_data_class

data class ProfileUpdateResponse(
    val id:Int,
    val email:String,
    val username:String,
    val password:String,
    val first_name:String,
    val last_name:String,
    val phone:String
    )
