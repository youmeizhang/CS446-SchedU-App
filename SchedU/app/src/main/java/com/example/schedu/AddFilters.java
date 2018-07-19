package com.example.schedu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AddFilters extends AppCompatActivity {
    private CheckBox filter1, filter2, filter3, filter4, filter5;
    private Button clear_filter, finish, back;
    private boolean f1, f2, f3, f4, f5, needed;
    private boolean[] filters = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filters);

        filter1 = findViewById(R.id.filter1);
        filter2 = findViewById(R.id.filter2);
        filter3 = findViewById(R.id.filter3);
        filter4 = findViewById(R.id.filter4);
        filter5 = findViewById(R.id.filter5);

        clear_filter = findViewById(R.id.filter_cleared);
        finish = findViewById(R.id.filter_finished);
        //back = findViewById(R.id.back_to_main);

        // filters = getIntent().getBooleanArrayExtra("filters");

        if(filter1.isChecked()) {
            // no class before 10am

        }

        filter1.setChecked(filters[0]);
        filter2.setChecked(filters[1]);
        filter3.setChecked(filters[2]);
        filter4.setChecked(filters[3]);
        filter5.setChecked(filters[4]);

        //set all checkboxes' checked or not

        clear_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //clear all filters
                filter1.setChecked(false);
                filter2.setChecked(false);
                filter3.setChecked(false);
                filter4.setChecked(false);
                filter5.setChecked(false);

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //store filters and go back to mainactivity
                Intent i = new Intent(AddFilters.this, MainActivity.class);
                filters[0] = filter1.isChecked();
                filters[1] = filter2.isChecked();
                filters[2] = filter3.isChecked();
                filters[3] = filter4.isChecked();
                filters[4] = filter5.isChecked();
                // System.out.println("content of filters 0-4 are: " + filters[0]);
                needed = true;
                i.putExtra("needed", needed);
                i.putExtra("filters", filters);
                startActivity(i);
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent back = new Intent(AddFilters.this, MainActivity.class);
//                back.putExtra("needed", needed);
//                startActivity(back);
//
//            }
//        });






    }

}
