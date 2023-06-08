package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST
import com.example.testregistr.Model.DataInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceRefresh {
    @Headers("Content-Type:application/json")
    @POST(CONST.REFRESHTOKEN)
    fun loadRepoRefresh(@Header("Authorization")  token : String): Call<DataInfo?>?

}