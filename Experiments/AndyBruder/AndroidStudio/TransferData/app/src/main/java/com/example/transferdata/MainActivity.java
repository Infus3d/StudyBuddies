package com.example.transferdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText display;
    Button displayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText) findViewById(R.id.send_text_id);
        displayButton = (Button) findViewById(R.id.display_button);

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = display.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Second_activity.class);

                intent.putExtra(“message_key”, s);
                startActivity(intent);
            }
        });

    }
}