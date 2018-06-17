package com.example.schedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {
    private TextView textView2;
    private Button btn3;
    private Button btnMon8;
    String value;
    private Context mContext;
    private Activity mActivity;
    private TextView tv;
    private TableLayout mRelativeLayout;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        value = getIntent().getStringExtra("getData");
        tv = (TextView) findViewById(R.id.tv);
        btn3 = (Button)findViewById(R.id.new_button);
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btnMon8.setText(value);
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        mContext = getApplicationContext();
        mActivity = Main2Activity.this;

        mRelativeLayout = (TableLayout) findViewById(R.id.rl);
        btnMon8 = (Button) findViewById(R.id.Mon8);
        btnMon8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View courseView = inflater.inflate(R.layout.coursedetail, null);

                mPopupWindow = new PopupWindow(
                        courseView,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                );

                ((TextView)mPopupWindow.getContentView().findViewById(R.id.tv)).setText(value);

                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                ImageButton closeButton = (ImageButton) courseView.findViewById(R.id.ib_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
            }
        });


    }
}












