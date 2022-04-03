package com.harshad.projectclean.APIRequests

object URLConstants {
    const val BASE_URL = "https://project-clean.herokuapp.com"

    const val LOGIN_URL = "/auth/token/login"
    const val REGISTER_URL = "/auth/users/"
    const val PROFILE_UPDATE_CREATE = "/auth/update-user"
    const val PROFILE_CREATE = "/auth/create-citi-profile"
    const val GET_LOGGED_USER = "/auth/users/me/"


    const val GET_GRI_DATA = "/api/get-gri-data/"
    const val UPLOAD_GRI = "/api/upload-gri/"
    const val UPVOTE_GRI = "/api/upvote-gri/"

}