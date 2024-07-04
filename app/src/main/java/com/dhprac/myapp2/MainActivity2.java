package com.dhprac.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView tv = findViewById(R.id.activity2_test_text);

        Intent intent = getIntent();
        String minute = intent.getStringExtra("minute");

        tv.setText("notification이 전송되었던 분 : " + minute);
    }
}