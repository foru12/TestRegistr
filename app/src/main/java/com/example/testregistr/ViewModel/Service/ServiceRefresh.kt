package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST
import com.example.testregistr.Model.DataInfo
import retrofit2.Call
import retrofit2.http.*

interface ServiceRefresh {
   

    @POST(CONST.REFRESHTOKEN)
    fun loadRepoRefresh(@Header("Authorization")  token : String,@Body tokenMap : MutableMap<String,String>): Call<DataInfo?>?

}