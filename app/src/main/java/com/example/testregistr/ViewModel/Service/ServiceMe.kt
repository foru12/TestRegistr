package com.example.testregistr.ViewModel.Service

import com.example.testregistr.CONST.CONST
import com.example.testregistr.CONST.CONST.acessToken
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import retrofit2.Call
import retrofit2.http.*

interface ServiceMe  {
    //@Headers({"Authorization:Bearer"})
    @GET(CONST.MEURL)
    fun loadRepoMe(@Header("Authorization")  token : String): Call<DataMe?>?


}