package com.example.testregistr.Model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DataMe(
    @SerializedName("name")
    var name: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("birthday")
    var birthday: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("vk")
    var vk: String?,
    @SerializedName("instagram")
    var instagram: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("id")
    var id :Int,
    @SerializedName("last")
    var last: Date?,
    @SerializedName("online")
    var online : Boolean?,
    @SerializedName("created")
    var created: Date?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("completed_task")
    var completed_task : Int?


)

