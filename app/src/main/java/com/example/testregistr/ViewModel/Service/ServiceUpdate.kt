package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST
import com.example.testregistr.Model.DataMe
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ServiceUpdate {
    @PUT(CONST.MEURL)
    fun loadRepoMeUpdate(@Header("Authorization")  token : String,jsonObj : JSONObject): Call<DataMe?>?
}