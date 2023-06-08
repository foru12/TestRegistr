package com.example.testregistr.Model

import com.google.gson.annotations.SerializedName

data class DataInfo(
    @SerializedName("refresh_token")
    var refresh_token: String,
    @SerializedName("access_token")
    var access_token: String,
    @SerializedName("user_id")
    var user_id :Int,
    @SerializedName("is_user_exists")
    var is_user_exists : Boolean
)
