package com.example.transferdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity2 extends AppCompatActivity {

    TextView viewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        viewDisplay = (TextView) findViewById(R.id.viewDisplay);

        String s = intent.getStringExtra("key");

        viewDisplay.setText(s);


    }
}