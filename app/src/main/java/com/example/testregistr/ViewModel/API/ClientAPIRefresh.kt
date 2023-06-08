package com.example.testregistr.ViewModel.API

import android.util.Log
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import com.example.testregistr.ViewModel.CallBackInterface
import com.example.testregistr.ViewModel.CallBackInterfaceRefresh
import com.example.testregistr.ViewModel.CallBackRequest
import com.example.testregistr.ViewModel.Service.ServiceCode
import com.example.testregistr.ViewModel.Service.ServiceRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPIRefresh : CallBackInterfaceRefresh {



    override fun executeRefresh(url: String?, callback: CallBackRequest?, token: String) {

        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: ServiceRefresh = retrofit.create(ServiceRefresh::class.java)





        val call: Call<DataInfo?>? = retrofitAPI.loadRepoRefresh("Bearer " + token)
        Log.e("Добавляем токен","--> " + "Bearer : " + token)
        call?.enqueue(object : Callback<DataInfo?> {
            override fun onResponse(call: Call<DataInfo?>, response: Response<DataInfo?>) {
                Log.e("Response","--> " + (response.body()?.access_token))
                Log.e("Response","--> " + (response.body()?.is_user_exists))
                Log.e("Response","--> " + (response.body()?.user_id))
                Log.e("Response","--> " + (response.body().toString()))



                Log.e("Новый токен -->", "" + response.body()?.access_token)





                if (response.code().toString() == "200"){
                    response.body()?.let { callback?.successReqCode(it) };


                }
                else callback?.errorReq("Error");





            }

            override fun onFailure(call: Call<DataInfo?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
                callback?.errorReq(t.toString());

            }

        })


    }


}