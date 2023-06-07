package com.example.testregistr.View


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.R
import com.example.testregistr.ViewModel.API.ClientAPICode
import com.example.testregistr.ViewModel.API.ClientAPINumber
import com.example.testregistr.ViewModel.CallBackRequest
import com.hbb20.CountryCodePicker


class Main : AppCompatActivity(),CallBackRequest {
    //country cod
    private lateinit var codePicker: CountryCodePicker
    private lateinit var edNubmer: EditText
    private lateinit var btnSignIn : Button
    private lateinit var btnOkSms : Button
    private lateinit var edSmsConfirmation : EditText

    private lateinit var layout_sms_ID : View
    private lateinit var layout_signin_ID : View
    private lateinit var layout_loading_ID : View
    val restClientApiNumber = ClientAPINumber()
    val restClientApiCode = ClientAPICode()
    val params: MutableMap<String, String> = HashMap()


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        setContentView(R.layout.activity_main)
        codePicker = findViewById(R.id.country_code)
        edNubmer = findViewById(R.id.edNubmer)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnOkSms = findViewById(R.id.btnOkSms)

        edSmsConfirmation = findViewById(R.id.edSmsConfirmation)

        layout_sms_ID = findViewById(R.id.layout_sms_ID)
        layout_signin_ID = findViewById(R.id.layout_signin_ID)
        layout_loading_ID = findViewById(R.id.layout_loading_ID)

        //edNubmer!!.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        params["phone"] = ""
        //startSendPhone(params)

        btnSignIn.setOnClickListener {


            //Добавить проверку на кол во симовлов в номере
            var number = edNubmer.text.toString()
            if (number != ""){
                layout_signin_ID.visibility = View.GONE
                layout_loading_ID.visibility = View.VISIBLE
                params["phone"] = number

                //Запуск проверки на авторизацию

                restClientApiNumber.execute(BASEURL,this,params)
            }else{
                Toast.makeText(this,"Enter your phone number",Toast.LENGTH_SHORT).show()
            }
        }

        btnOkSms.setOnClickListener{
            var code = edSmsConfirmation.text.toString()
            if (code != ""){
                //layout_signin_ID.visibility = View.GONE
                //layout_loading_ID.visibility = View.VISIBLE
                params["code"] = code

                //Запуск проверки на авторизацию

                restClientApiCode.execute(BASEURL,this,params)
            }else{
                Toast.makeText(this,"Enter your code",Toast.LENGTH_SHORT).show()
            }

        }



    }

    override fun successReq(response: String?) {
        Log.e("CoolBack","--> " + response)
        if (response.toString() == "201"){
            //Добавить код из смс
            Log.e("Check","true")
            layout_loading_ID.visibility = View.GONE
            layout_sms_ID.visibility = View.VISIBLE




        }
        else{
            Toast.makeText(this,"Error Server",Toast.LENGTH_SHORT).show()
        }


    }

    override fun successReqCode(response: DataInfo?) {
        Log.e("CoolBack","--> " + response)



        editor.putString("access_token", response?.access_token)
        editor.putString("refresh_token", response?.refresh_token)
        response?.is_user_exists?.let { editor.putBoolean("is_user_exists", it) }
        response?.user_id?.let { editor.putInt("user_id", it) }
        editor.apply()



        Log.e("Save", "access_token" + "--> "+ sharedPreferences.getString("access_token", "")!!)
        Log.e("Save", "refresh_token" + "--> "+sharedPreferences.getString("refresh_token", "")!!)
        Log.e("Save","is_user_exists" + "--> "+ sharedPreferences.getString("is_user_exists", "")!!)
        Log.e("Save", "user_id" + "--> "+sharedPreferences.getString("user_id", "")!!)


        if (sharedPreferences.getBoolean("is_user_exists",false)){
            //Запуск профиля

        }else{
            //Запуск регистрации 
        }





    }


    override fun errorReq(error: String?) {
        Log.e("CoolBack","--> " + error)
    }





}