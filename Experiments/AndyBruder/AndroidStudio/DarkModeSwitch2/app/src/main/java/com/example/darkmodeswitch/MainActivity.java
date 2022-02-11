package com.example.darkmodeswitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SwitchCompat s;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = findViewById(R.id.switchID);
        layout = findViewById(R.id.layoutID);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(s.isChecked()) {
                    layout.setBackgroundColor(getResources().getColor(R.color.darkModeBackground));
                    s.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    layout.setBackgroundColor(getResources().getColor(R.color.white));
                    s.setTextColor(getResources().getColor(R.color.black));
                }

            }
        });
    }
}