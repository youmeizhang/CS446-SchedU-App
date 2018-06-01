package com.example.schedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView textView2;
    private Button btn3;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final String value = getIntent().getStringExtra("getData");

        textView2 = (TextView)findViewById(R.id.Mon8);
        btn3 = (Button)findViewById(R.id.new_button);
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textView2.setText(value);
            }
        });
    }
}
