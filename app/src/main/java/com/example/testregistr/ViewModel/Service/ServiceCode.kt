package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST
import com.example.testregistr.CONST.CONST.CHECKAUTHCODE
import com.example.testregistr.Model.DataInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceCode {
    @Headers("Content-Type:application/json")
    @POST(CHECKAUTHCODE)
    fun loadRepoApiCode(@Body code: MutableMap<String, String>): Call<DataInfo?>?
}