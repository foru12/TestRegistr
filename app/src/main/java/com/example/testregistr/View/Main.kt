package com.example.testregistr.View


import android.annotation.SuppressLint
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
import com.example.testregistr.CONST.CONST.refreshToken
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import com.example.testregistr.R
import com.example.testregistr.ViewModel.API.*
import com.example.testregistr.ViewModel.CallBack.CallBackRequest
import com.example.testregistr.ViewModel.TextEdit.CurrencyTextWatcher
import com.hbb20.CountryCodePicker
import java.util.*


class Main : AppCompatActivity(), CallBackRequest {
    //country cod
    private lateinit var codePicker: CountryCodePicker

    private lateinit var edNubmer: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnOkSms: Button
    private lateinit var btnRegistr: Button
    private lateinit var edSmsConfirmation: EditText
    private lateinit var edUserNameRegistr: EditText
    private lateinit var edNumberRegistr: EditText
    private lateinit var edNameRegistr: EditText

    private lateinit var layout_sms_ID: View
    private lateinit var layout_signin_ID: View
    private lateinit var layout_loading_ID: View
    private lateinit var layout_regist_ID: View
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
        setContentView(R.layout.activity_main)


        setId()
        setClick()


    }

    @SuppressLint("ResourceAsColor")
    private fun setClick() {

        btnSignIn.setOnClickListener {
            //Добавить проверку на кол во симовлов в номере
            var number = codePicker.fullNumber
            Log.e("Number","--> " + codePicker.fullNumber)
            if (edNubmer.text.toString() != "") {
                layout_signin_ID.visibility = View.GONE
                layout_loading_ID.visibility = View.VISIBLE
                params["phone"] = number

                Log.e("Запуск проверки номера", "...")
                //Запуск проверки на авторизацию

                restClientApiNumber.execute(BASEURL, this, params)
            } else {

                edNubmer.getBackground().mutate()
                    .setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                edNubmer.setTextColor(Color.RED)

                Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show()
            }
        }

        btnOkSms.setOnClickListener {
            var code = edSmsConfirmation.text.toString()
            if (code != "" && code == "133337") {
                layout_signin_ID.visibility = View.GONE
                layout_loading_ID.visibility = View.VISIBLE
                params["code"] = code

                //Запуск проверки на авторизацию

                Log.e("Проверка на наличие такого номера в базе", "...")

                restClientApiCode.execute(BASEURL, this, params)
            } else {
                Toast.makeText(this, "Enter your code", Toast.LENGTH_SHORT).show()
            }

        }

        btnRegistr.setOnClickListener {
            var number = edNumberRegistr.text.toString()
            var name = edNameRegistr.text.toString()
            var userName = edUserNameRegistr.text.toString()

            if (number != "" && name != "" && userName != "") {

                Log.e("Click", "Registr" + "...")

                paramsRegistr["phone"] = number
                paramsRegistr["name"] = name
                paramsRegistr["username"] = userName
                Log.e("Отправялем данные на сервер",  "...")
                Log.e("Registr", "" + "-->" + paramsRegistr.toString())

                restClientApiRegistr.execute(BASEURL, this, paramsRegistr)
            } else {
                Toast.makeText(this, "Enter your info", Toast.LENGTH_SHORT).show()
            }


        }

        edNubmer.setOnClickListener {
            edNubmer.setTextColor(Color.BLACK)
            edNubmer.getBackground().mutate()
                .setColorFilter(R.color.black, PorterDuff.Mode.SRC_ATOP);
        }

        edNubmer.addTextChangedListener(CurrencyTextWatcher())
        codePicker.registerCarrierNumberEditText(edNubmer)


    }

    private fun setId() {
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE)
        editor = sharedPreferences.edit()
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


    }

    override fun successReq(response: String?) {
        Log.e("CoolBack", "--> " + response)
        if (response.toString() == "201") {
            //Добавить код из смс
            Log.e("Проверка номера успешно", "...")
            layout_loading_ID.visibility = View.GONE
            layout_sms_ID.visibility = View.VISIBLE


        } else {
            Toast.makeText(this, "Error Server", Toast.LENGTH_SHORT).show()
        }


    }

    override fun successReqRegistr(response: DataInfo?) {
        Log.e("Сохраняем токен после регистрации", "-->" + response?.access_token.toString())
        saveTokens(response?.access_token,response?.refresh_token)
        if (response?.is_user_exists == false) {
            startProfile(response?.access_token.toString())
        }
    }


    override fun successReqCode(response: DataInfo?) {
        Log.e("CoolBack", "--> " + response)
        Log.e(
            "Сохраняем токены",
            "-->" + "\n" + "access_token:" + response?.access_token.toString() + "\n" + "access_token:" + response?.access_token.toString()
        )

        saveTokens(response?.access_token, response?.refresh_token)

        response?.is_user_exists?.let { editor.putBoolean("is_user_exists", it) }
        response?.user_id?.let { editor.putInt("user_id", it) }
        editor.apply()

        Log.e(
            "Сохраняем другие значения",
            "-->" + "\n" + "is_user_exists:" + response?.is_user_exists.toString() + "\n" + "user_id:" + response?.user_id.toString()
        )

        Log.e(
            "Save",
            "is_user_exists" + "--> " + sharedPreferences.getBoolean("is_user_exists", false)!!
        )
        Log.e("Save", "user_id" + "--> " + sharedPreferences.getInt("user_id", 0)!!)

        if (sharedPreferences.getBoolean("is_user_exists", false)) {
            //Запуск профиля
            Log.e("Запуск профиля", "...")
            layout_sms_ID.visibility = View.GONE
            startProfile(response?.access_token.toString())
        } else {
            //Запуск регистрации
            Log.e("Запуск регистрации", "...")
            layout_sms_ID.visibility = View.GONE
            layout_loading_ID.visibility = View.GONE
            layout_regist_ID.visibility = View.VISIBLE
        }
    }

    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        editor.putString("access_token", accessToken)
        editor.putString("refresh_token", refreshToken)
        editor.apply()

    }

    private fun startProfile(token: String) {
        val params: MutableMap<String, String> = HashMap()
        params["refresh_token"] = sharedPreferences.getString("access_token","").toString()
        restClientApiMe.executeRefresh(BASEURL, this, token,params)
    }

    override fun successReqMe(response: DataMe?) {

        Log.e("CallBack", "--> " + response.toString())


        if (response == null) {
            Log.e("Опа!", "Токен пора бы и обновить вообще-то")

            val params: MutableMap<String, String> = HashMap()
            params["refresh_token"] = sharedPreferences.getString("access_token","").toString()
            restClientApiRefresh.executeRefresh(BASEURL, this, refreshToken,params)


        } else {
            Log.e("Callback", "--> " + "Save info about me")
            Log.e("Callback", "--> " + "Было бы что сохранять")
            Log.e("Загружаем данные профиля", "..." + "")

            //save Data Me
            saveData(response)
            //переход в профиль
            startActivity(Intent(this, Profile::class.java))
            finish()
        }


    }

    private fun saveData(response: DataMe?) {
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
        editor.putString("created", response?.created.toString())
        editor.putString("phone", response?.phone.toString())



        response?.completed_task?.let { editor.putInt("completed_task", it) }

        editor.apply()
    }

    override fun onBackPressed() {

    }


    override fun errorReq(error: String?) {
        Log.e("CoolBack", "--> " + error)
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }


}