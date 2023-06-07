package com.example.testregistr.View


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.R
import com.example.testregistr.ViewModel.CallBackRequest

import com.example.testregistr.ViewModel.ClientAPI
import com.hbb20.CountryCodePicker


class Main : AppCompatActivity(),CallBackRequest {
    //country cod
    private lateinit var codePicker: CountryCodePicker
    private lateinit var edNubmer: EditText
    private lateinit var btnSignIn : Button
    private lateinit var containerSignIn : LinearLayout
    private lateinit var progresLoad : ProgressBar
    private lateinit var edSmsConfirmation : EditText
    private lateinit var layout_sms_ID : View
    private lateinit var layout_signin_ID : View
    val restClientApi = ClientAPI()
    val params: MutableMap<String, String> = HashMap()
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        codePicker = findViewById(R.id.country_code)
        edNubmer = findViewById(R.id.edNubmer)
        btnSignIn = findViewById(R.id.btnSignIn)
        containerSignIn = findViewById(R.id.containerSignIn)
        progresLoad = findViewById(R.id.progresLoad)
        edSmsConfirmation = findViewById(R.id.edSmsConfirmation)

        layout_sms_ID = findViewById(R.id.layout_sms_ID)
        layout_signin_ID = findViewById(R.id.layout_signin_ID)

        //edNubmer!!.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        params["phone"] = ""
        //startSendPhone(params)

        btnSignIn.setOnClickListener {


            //Добавить проверку на кол во симовлов в номере
            var number = edNubmer.text.toString()
            if (number != ""){
                containerSignIn.visibility = View.GONE
                progresLoad.visibility = View.VISIBLE
                params["phone"] = number

                //Запуск проверки на авторизацию

                restClientApi.execute(BASEURL,this,params)
            }else{
                Toast.makeText(this,"Enter your phone number",Toast.LENGTH_SHORT).show()
            }





        }


    }

    override fun successReq(response: String?) {
        Log.e("CoolBack","--> " + response)
        if (response.toString() == "201"){
            //Добавить код из смс
           // progresLoad.visibility = View.GONE
            //layout_sms_ID.visibility = View.VISIBLE




        }else{
            Toast.makeText(this,"Error Server",Toast.LENGTH_SHORT).show()
        }


    }

    override fun errorReq(error: String?) {
        Log.e("CoolBack","--> " + error)
    }





}