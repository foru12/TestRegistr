package com.example.testregistr


import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
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
        restClientApi.sendAuthNumber("string")
    }

    private fun getTemplates(text: String): String {
        //add
        return ""
    }
}