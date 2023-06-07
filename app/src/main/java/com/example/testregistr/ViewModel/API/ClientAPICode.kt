package com.example.testregistr.ViewModel.API

import android.util.Log
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.ViewModel.CallBackInterface
import com.example.testregistr.ViewModel.CallBackRequest
import com.example.testregistr.ViewModel.Service.ServiceCode
import com.example.testregistr.ViewModel.Service.ServicePhone
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPICode : CallBackInterface {


    override fun execute(url: String?, callback: CallBackRequest?, phoneMap:MutableMap<String, String>) {

        Log.e("Start Post request","...")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: ServiceCode = retrofit.create(ServiceCode::class.java)


        val call: Call<DataInfo?>? = retrofitAPI.loadRepoApiCode(phoneMap)
        call?.enqueue(object : Callback<DataInfo?> {
            override fun onResponse(call: Call<DataInfo?>, response: Response<DataInfo?>) {
                Log.e("Response","--> " + (response.body()?.access_token))
                Log.e("Response","--> " + (response.body()?.is_user_exists))
                Log.e("Response","--> " + (response.body()?.user_id))
                Log.e("Response","--> " + (response.body().toString()))



                //val params: MutableMap<String, String> = HashMap()
              //  params[response.] = ""


                if (response.code().toString() == "200"){
                    response.body()?.let { callback?.successReqCode(it) };


                }





            }

            override fun onFailure(call: Call<DataInfo?>, t: Throwable) {
                Log.e("onFailure","--> " + t.toString())
                //callback?.errorReq(t.toString());

            }

        })

    }


}