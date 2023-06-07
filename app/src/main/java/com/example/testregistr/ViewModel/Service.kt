package com.example.testregistr.ViewModel

import com.example.testregistr.CONST.CONST.SENDAUTHCODEURL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface Service {
    @Headers("Content-Type:application/json")
    @POST(SENDAUTHCODEURL)
    fun loadRepoApi(@Body phoneMap: MutableMap<String, String>): Call<ResponseBody?>?
}