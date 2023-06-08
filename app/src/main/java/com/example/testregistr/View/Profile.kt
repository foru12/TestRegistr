package com.example.testregistr.View

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.testregistr.CONST.CONST.AVATARURLIMAGE
import com.example.testregistr.CONST.CONST.BASEURL
import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import com.example.testregistr.R
import com.example.testregistr.ViewModel.API.ClientAPIMeUpdate
import com.example.testregistr.ViewModel.API.ClientAPIRefresh
import com.example.testregistr.ViewModel.CallBack.CallBackRequest
import org.json.JSONObject

class Profile : AppCompatActivity(), CallBackRequest {


    private lateinit var txtNumber: EditText
    private lateinit var txtName: EditText
    private lateinit var txtCity: EditText
    private lateinit var txtDate: EditText
    private lateinit var txtZodiac: EditText
    private lateinit var txtAbout: EditText

    private lateinit var imAvatar: ImageView
    private lateinit var imEdit: ImageView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val restClientAPIMeUpdate = ClientAPIMeUpdate()
    val restClientApiRefresh = ClientAPIRefresh()


    private var flag = true
    private var accesToken = ""

    val jsonObject = JSONObject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        setId()
        setClick()
        setStartInfo()
    }

    private fun setClick() {

        imEdit.setOnClickListener {
            if (flag) {
                setEdit(R.drawable.back_edit)
                setEnable(true)
                flag = false;
            } else {
                flag = true;
                setEdit(R.drawable.back_sms)
                setEnable(false)
                changeDataMe()


            }


        }
    }

    private fun changeDataMe() {
        editor.putString("birthday", txtDate.text.toString())
        editor.putString("city", txtCity.text.toString())
        //about me
        editor.putString("abaoutMe", txtAbout.text.toString())

        editor.apply()




        jsonObject.put("birthday", sharedPreferences.getString("birthday",""))
        jsonObject.put("city", sharedPreferences.getString("city",""))
        jsonObject.put("avatar", "")

        getToken()









    }

    private fun getToken(){
        var access_token = sharedPreferences.getString("access_token","")
        Log.e("Profile","Ваш рефреш токен --->" + access_token)
        restClientApiRefresh.executeRefresh(BASEURL,this, access_token.toString())



    }

    private fun setEnable(b: Boolean) {
        txtCity.isEnabled = b
        txtDate.isEnabled = b
        txtZodiac.isEnabled = b
        txtAbout.isEnabled = b
    }

    private fun setEdit(backEdit: Int) {
        txtCity.setBackgroundResource(backEdit)
        txtDate.setBackgroundResource(backEdit)
        txtZodiac.setBackgroundResource(backEdit)
        txtAbout.setBackgroundResource(backEdit)
    }


    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    private fun setStartInfo() {
        Log.e("Загружаем данные профиля","...")
        txtNumber.text = sharedPreferences.getString("phone", "none")!!.toEditable()
        txtName.text = sharedPreferences.getString("name", "none")!!.toEditable()
        txtCity.text = sharedPreferences.getString("city", "none")!!.toEditable()
        txtDate.text = sharedPreferences.getString("birthday", "none")!!.toEditable()
        txtZodiac.text = sharedPreferences.getString("Taurus","none")!!.toEditable()
        var str = java.lang.StringBuilder()
        str
            .append(sharedPreferences.getString("vk","none"))
            .append("\n")
            .append(sharedPreferences.getString("instagram","none"))
            .append("\n")
            .append(sharedPreferences.getString("status","none"))
            .append("\n")
            .append(sharedPreferences.getString("last","none"))
            .append("\n")
            .append(sharedPreferences.getString("completed_task","none"))
            .append("\n")
        txtAbout.text = str.toString().toEditable()
        Glide.with(this).load(sharedPreferences.getString("avatar",AVATARURLIMAGE)).into(imAvatar);



    }

    private fun setId() {

        txtNumber = findViewById(R.id.txtNumber)
        txtName = findViewById(R.id.txtName)
        txtCity = findViewById(R.id.txtCity)
        txtDate = findViewById(R.id.txtDate)
        txtZodiac = findViewById(R.id.txtZodiac)
        txtAbout = findViewById(R.id.txtAbout)
        imAvatar = findViewById(R.id.imAvatar)
        imEdit = findViewById(R.id.imEdit)
    }

    override fun successReq(response: String?) {


    }

    override fun successReqRegistr(response: DataInfo?) {

    }

    override fun successReqCode(response: DataInfo?) {


        Log.e("Profile","Обновили токен")

        accesToken = response?.access_token.toString()
        restClientAPIMeUpdate.executeUpdate(BASEURL,this,accesToken, jsonObject)
    }

    override fun successReqMe(response: DataMe?) {
        Toast.makeText(this,"Data updated!",Toast.LENGTH_SHORT).show()
    }

    override fun errorReq(error: String?) {

    }
}