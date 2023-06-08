package com.example.testregistr.View


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.CONST.CONST.acessToken
import com.example.testregistr.CONST.CONST.refreshToken
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import com.example.testregistr.R
import com.example.testregistr.ViewModel.API.*
import com.example.testregistr.ViewModel.CallBackRequest
import com.hbb20.CountryCodePicker


class Main : AppCompatActivity(),CallBackRequest {
    //country cod
    private lateinit var codePicker: CountryCodePicker
    private lateinit var edNubmer: EditText
    private lateinit var btnSignIn : Button
    private lateinit var btnOkSms : Button
    private lateinit var btnRegistr : Button
    private lateinit var edSmsConfirmation : EditText
    private lateinit var edUserNameRegistr : EditText
    private lateinit var edNumberRegistr : EditText
    private lateinit var edNameRegistr : EditText

    private lateinit var layout_sms_ID : View
    private lateinit var layout_signin_ID : View
    private lateinit var layout_loading_ID : View
    private lateinit var layout_regist_ID : View
    val restClientApiNumber = ClientAPINumber()
    val restClientApiCode = ClientAPICode()
    val restClientApiRegistr = ClientAPIRegistr()
    val restClientApiMe = ClientAPIMe()
    val restClientApiRefresh = ClientAPIRefresh()
    val params: MutableMap<String, String> = HashMap()
    val paramsRegistr: MutableMap<String, String> = HashMap()


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        setContentView(R.layout.activity_main)
        codePicker = findViewById(R.id.country_code)
        edNubmer = findViewById(R.id.edNubmer)
        edUserNameRegistr = findViewById(R.id.edUserNameRegistr)
        edNumberRegistr = findViewById(R.id.edNumberRegistr)
        edNameRegistr = findViewById(R.id.edNameRegistr)


        btnSignIn = findViewById(R.id.btnSignIn)
        btnOkSms = findViewById(R.id.btnOkSms)
        btnRegistr = findViewById(R.id.btnRegistr)

        edSmsConfirmation = findViewById(R.id.edSmsConfirmation)

        layout_sms_ID = findViewById(R.id.layout_sms_ID)
        layout_signin_ID = findViewById(R.id.layout_signin_ID)
        layout_loading_ID = findViewById(R.id.layout_loading_ID)
        layout_regist_ID = findViewById(R.id.layout_regist_ID)



        params["phone"] = ""



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
              //  edNubmer.setBackgroundColor(Color.RED)
                edNubmer.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

                Toast.makeText(this,"Enter your phone number",Toast.LENGTH_SHORT).show()
            }
        }

        btnOkSms.setOnClickListener{
            var code = edSmsConfirmation.text.toString()
            if (code != ""){
                layout_signin_ID.visibility = View.GONE
                layout_loading_ID.visibility = View.VISIBLE
                params["code"] = code

                //Запуск проверки на авторизацию

                restClientApiCode.execute(BASEURL,this,params)
            }else{
                Toast.makeText(this,"Enter your code",Toast.LENGTH_SHORT).show()
            }

        }

        btnRegistr.setOnClickListener {
            var number = edNumberRegistr.text.toString()
            var name = edNameRegistr.text.toString()
            var userName = edUserNameRegistr.text.toString()

            if (number != "" && name != "" && userName != ""){

                Log.e("Click","Registr" + "...")

                paramsRegistr["phone"] = number
                paramsRegistr["name"] = name
                paramsRegistr["username"] = userName

                Log.e("Click","Registr" + "-->" + paramsRegistr.toString())

                restClientApiRegistr.execute(BASEURL,this,paramsRegistr)
            }else{
                Toast.makeText(this,"Enter your info",Toast.LENGTH_SHORT).show()
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

    override fun successReqRegistr(response: DataInfo?) {


        Log.e("Сохраняем токен2",  "-->" + response?.access_token.toString())



        editor.putString("access_token", response?.access_token)
        editor.putString("refresh_token", response?.refresh_token)
        editor.apply()
        updateToken()


        if (response?.is_user_exists == false){

            startProfile(response?.access_token.toString())


        }





    }

    private fun updateToken() {
      //  acessToken = sharedPreferences.getString("access_token","").toString()
      //  refreshToken = sharedPreferences.getString("refresh_token","").toString()




    }

    override fun successReqCode(response: DataInfo?) {

        Log.e("CoolBack","--> " + response)


        Log.e("Сохраняем токен",  "-->" + response?.access_token.toString())

        editor.putString("access_token", response?.access_token)
        editor.putString("refresh_token", response?.refresh_token)
        editor.apply()

        updateToken()
        //переделать рефреш





        response?.is_user_exists?.let { editor.putBoolean("is_user_exists", it) }
        response?.user_id?.let { editor.putInt("user_id", it) }
        editor.apply()


        Log.e("Save","is_user_exists" + "--> "+ sharedPreferences.getBoolean("is_user_exists", false)!!)
        Log.e("Save", "user_id" + "--> "+sharedPreferences.getInt("user_id", 0)!!)


        if (sharedPreferences.getBoolean("is_user_exists",false)){
            //Запуск профиля
            Log.e("Запуск профиля","...")


            layout_sms_ID.visibility = View.GONE


            startProfile(response?.access_token.toString())


        }else{
            //Запуск регистрации
            Log.e("Запуск регистрации","...")











            layout_sms_ID.visibility = View.GONE
            layout_loading_ID.visibility = View.GONE
            layout_regist_ID.visibility = View.VISIBLE



        }





    }

    private fun startProfile(token:String) {
        Log.e("StartProfile","-->"  + "access_token = " + sharedPreferences.getString("access_token","").toString())



        restClientApiMe.executeRefresh(BASEURL,this,token)


    }

    override fun successReqMe(response: DataMe?) {

        Log.e("CallBack", "--> " + response.toString())

        //если ничего не пришло то нужно обновить токен
        if (response == null){
            Log.e("Опа!","Токен пора бы и обновить вообще-то")


            restClientApiRefresh.executeRefresh(BASEURL,this, refreshToken)


        }
        else{
            Log.e("Callback", "--> " + "Save info about me")
            Log.e("Callback", "--> " + "Было бы что сохранять")

            //save Data Me
            editor.putString("name", response?.name)
            editor.putString("username", response?.username)
            editor.putString("birthday", response?.birthday)
            editor.putString("city", response?.city)
            editor.putString("vk", response?.vk)
            editor.putString("instagram", response?.instagram)
            editor.putString("status", response?.status)
            editor.putString("avatar", response?.avatar)

            response?.id?.let { editor.putInt("id", it) }
            editor.putString("last", response?.last.toString())
            response?.online?.let { editor.putBoolean("online", it) }
            editor.putString("created",response?.created.toString())
            editor.putString("phone",response?.phone.toString())



            response?.completed_task?.let { editor.putInt("completed_task", it) }

            editor.apply()
            //переход в профиль
            startActivity(Intent(this,Profile::class.java))
            finish()
        }





    }

    override fun onBackPressed() {

    }


    override fun errorReq(error: String?) {
        Log.e("CoolBack","--> " + error)
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }





}