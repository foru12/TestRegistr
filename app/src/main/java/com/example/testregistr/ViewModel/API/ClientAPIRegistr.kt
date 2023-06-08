package com.example.testregistr.ViewModel.API

import android.util.Log
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.ViewModel.CallBack.CallBackInterface
import com.example.testregistr.ViewModel.CallBack.CallBackRequest
import com.example.testregistr.ViewModel.Service.ServiceRegistr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPIRegistr : CallBackInterface {


    override fun execute(url: String?, callback: CallBackRequest?, phoneMap:MutableMap<String, String>) {

        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: ServiceRegistr = retrofit.create(ServiceRegistr::class.java)


        val call: Call<DataInfo?>? = retrofitAPI.loadRepoRegistr(phoneMap)
        call?.enqueue(object : Callback<DataInfo?> {
            override fun onResponse(call: Call<DataInfo?>, response: Response<DataInfo?>) {

                Log.e("Response","--> " + (response))







                Log.e("Response","--> " + (response.body()?.access_token))
                Log.e("Response","--> " + (response.body()?.refresh_token))
                Log.e("Response","--> " + (response.body()?.user_id))
                Log.e("Response","--> " + (response.code().toString()))

                if (response.code().toString() != "201"){

                    callback?.errorReq("Error");

                }else if (response.code() == null){
                    callback?.errorReq("Error");
                }
                else{
                    callback?.successReqRegistr(response.body())
                }






            }

            override fun onFailure(call: Call<DataInfo?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
                callback?.errorReq(t.toString());

            }

        })

    }


}