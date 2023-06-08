package com.example.testregistr.ViewModel.API

import android.util.Log
import com.example.testregistr.Model.DataMe
import com.example.testregistr.ViewModel.CallBackInterfaceRefresh
import com.example.testregistr.ViewModel.CallBackInterfaceUpdate
import com.example.testregistr.ViewModel.CallBackRequest
import com.example.testregistr.ViewModel.Service.ServiceMe
import com.example.testregistr.ViewModel.Service.ServiceUpdate
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPIMeUpdate : CallBackInterfaceUpdate {




    override fun executeUpdate(url: String?, callback: CallBackRequest?,token : String,jsonObj : JSONObject) {
        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: ServiceUpdate = retrofit.create(ServiceUpdate::class.java)







        val call: Call<DataMe?>? = retrofitAPI.loadRepoMeUpdate("Bearer " + token,jsonObj)
        Log.e("Добавляем токен","--> " + "Bearer : " + token)
        Log.e("Добавляем jsonObj","--> " + "jsonObj  : " + jsonObj)
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