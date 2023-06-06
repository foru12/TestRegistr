package com.example.testregistr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    //country cod
    private CountryCodePicker codePicker;
    private EditText edNubmer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        codePicker = findViewById(R.id.country_code);
        edNubmer = findViewById(R.id.edNubmer);


        edNubmer.addTextChangedListener(new PhoneNumberFormattingTextWatcher());















    }


    private String getTemplates(String text){
        //add
        return "";
    }
}