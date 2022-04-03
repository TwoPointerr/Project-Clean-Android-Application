package com.harshad.projectclean.APIRequests.authentication_data_class

data class RegisterRequest(val username:String,
                           val first_name:String,
                           val last_name:String,
                           val email:String,
                           val password:String,
                           val re_password:String,)
