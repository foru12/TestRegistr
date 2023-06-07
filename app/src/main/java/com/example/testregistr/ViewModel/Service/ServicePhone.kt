package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST.SENDAUTHCODEURL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ServicePhone {
    @Headers("Content-Type:application/json")
    @POST(SENDAUTHCODEURL)
    fun loadRepoApiPhone(@Body phoneMap: MutableMap<String, String>): Call<ResponseBody?>?
}