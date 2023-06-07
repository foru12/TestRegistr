package com.example.testregistr.ViewModel

import android.util.Log
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.Model.DataInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ClientAPI : CallBackInterface{


    override fun execute(url: String?, callback: CallBackRequest?,phoneMap:MutableMap<String, String>) {

        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: Service = retrofit.create(Service::class.java)


        val call: Call<ResponseBody?>? = retrofitAPI.loadRepoApi(phoneMap)
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Log.e("Response","--> " + response.body())
                Log.e("Response","--> " + response.code().toString())
                Log.e("Response","--> " + response.headers())
                Log.e("Response","--> " + response.message())
                Log.e("Response","--> " + response.toString())
                if (response.code().toString() == "201"){
                    Log.e("Success","true")
                    callback?.successReq(response.code().toString());
                }


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
                callback?.errorReq(t.toString());

            }

        })

    }


}