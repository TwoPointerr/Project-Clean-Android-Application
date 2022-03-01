package com.harshad.projectclean.APIRequests.authentication_data_class

data class CurrentLoggedUserResponse(val first_name:String,
                                     val last_name:String,
                                     val username:String,
                                     val id:Int,
                                     val email:String)
