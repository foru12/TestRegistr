package com.example.testregistr


import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.testregistr.ViewModel.ClientAPI
import com.hbb20.CountryCodePicker


class Main : AppCompatActivity() {
    //country cod
    private var codePicker: CountryCodePicker? = null
    private var edNubmer: EditText? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        codePicker = findViewById<CountryCodePicker>(R.id.country_code)
        edNubmer = findViewById<EditText>(R.id.edNubmer)
        //edNubmer!!.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        val restClientApi = ClientAPI()
        val params: MutableMap<String, String> = HashMap()
        params["phone"] = "0"


        
        if (restClientApi.sendAuthNumber(params)){
            Log.e("User ", "--> " + "found");
        }else  Log.e("User ", "--> " + "Not found");

    }

    private fun getTemplates(text: String): String {
        //add
        return ""
    }
}