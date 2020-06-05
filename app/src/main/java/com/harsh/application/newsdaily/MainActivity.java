package com.harsh.application.newsdaily;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        QueryUtils.extractFeatureFromJson("http://newsapi.org/v2/top-headlines?country=in&apiKey=0a85e88440d543b6ab2adb907c7613dd");



    }
}