package com.example.testregistr.ViewModel

import android.util.Log
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.Model.DataInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ClientAPI : SendAuth{

    override fun sendAuthNumber(phone:String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: Service = retrofit.create(Service::class.java)
        var modal = DataInfo(phone)
        val call: Call<DataInfo?>? = retrofitAPI.loadRepo(modal)
        call?.enqueue(object : Callback<DataInfo?> {
            override fun onResponse(call: Call<DataInfo?>, response: Response<DataInfo?>) {
                Log.e("Response","--> " + response.body())

            }

            override fun onFailure(call: Call<DataInfo?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
            }

        })

    }


}