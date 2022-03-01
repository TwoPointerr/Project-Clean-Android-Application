package com.harshad.projectclean.APIRequests.grievance_data_class

data class SingleGrievanceResponse(val id:Int,
                                   val gri_title:String,
                                   val gri_img:String,
                                   val gri_desc:String,
                                   val gri_category: CategoryData,
                                   val gri_upvote:Int,
                                   val gri_priority:Int,
                                   val gri_uploaded_user: UploadedUserProfile,
                                   val gri_locationResponse: GrievanceLocationResponse,
                                   val gri_timeStamp:String)
