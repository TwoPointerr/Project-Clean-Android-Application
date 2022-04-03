package com.harshad.projectclean.APIRequests.grievance_data_class

data class GrievanceLocationResponse(val id:Int,
                                     val loc_lat:Double,
                                     val loc_long:Double,
                                     val loc_display_name:String,
                                     val loc_suburb:String,
                                     val loc_city:String,
                                     val loc_postcode:Long
                                     )
