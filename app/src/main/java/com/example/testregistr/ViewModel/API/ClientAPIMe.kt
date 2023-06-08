package com.example.testregistr.ViewModel.API

import android.util.Log
import com.example.testregistr.CONST.CONST.acessToken
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import com.example.testregistr.ViewModel.CallBackInterface
import com.example.testregistr.ViewModel.CallBackInterfaceRefresh
import com.example.testregistr.ViewModel.CallBackRequest
import com.example.testregistr.ViewModel.Service.ServiceCode
import com.example.testregistr.ViewModel.Service.ServiceMe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPIMe : CallBackInterfaceRefresh {




    override fun executeRefresh(url: String?, callback: CallBackRequest?, token: String) {
        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: ServiceMe = retrofit.create(ServiceMe::class.java)


        val call: Call<DataMe?>? = retrofitAPI.loadRepoMe("Bearer " + token)
        Log.e("Добавляем токен","--> " + "Bearer : " + token)
        call?.enqueue(object : Callback<DataMe?> {
            override fun onResponse(call: Call<DataMe?>, response: Response<DataMe?>) {


                Log.e("Response","Me" + "-->" + response.body())


                callback?.successReqMe(response.body());





            }

            override fun onFailure(call: Call<DataMe?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
                callback?.errorReq(t.toString());

            }

        })

    }


}