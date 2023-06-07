package com.example.testregistr.ViewModel

import com.example.testregistr.CONST.CONST.SENDAUTHCODEURL
import com.example.testregistr.Model.DataInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Service {
    @Headers("Content-Type: application/json","accept: application/json")
    @POST(SENDAUTHCODEURL)

    fun loadRepo(@Body dataInfo: DataInfo): Call<DataInfo?>?
}