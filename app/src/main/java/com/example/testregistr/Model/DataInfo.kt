package com.example.testregistr.Model

data class DataInfo(
    var refresh_token: String,
    var access_token: String,
    var user_id :Int,
    var is_user_exists : Boolean
)
