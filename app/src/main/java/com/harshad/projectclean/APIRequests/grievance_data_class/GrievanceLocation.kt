package com.harshad.projectclean.APIRequests.grievance_data_class

data class GrievanceLocation(val id:Int,
                             val loc_lat:Float,
                             val loc_long:Float,
                             val loc_display_name:String,
                             val loc_suburb:String,
                             val loc_city:String,
                             val loc_municipality:String,
                             val loc_state_distric:String,
                             val loc_state:String,
                             val loc_postcode:Long)
