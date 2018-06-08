package com.example.testlistview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.DbHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText name, loc, desig;
    Spinner spnname;
    Button saveBtn, btnShow;
    Intent intent;
    String spn_res;
    TextView display_res2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.txtName);
        loc = (EditText) findViewById(R.id.txtLocation);
        desig = (EditText) findViewById(R.id.txtDesignation);
        spnname = (Spinner) findViewById(R.id.spinner_name);
        display_res2 = (TextView) findViewById(R.id.display_res);

        spnname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spn_res = parent.getItemAtPosition(position).toString();
                spn_res = spnname.getSelectedItem().toString();
                System.out.println(spn_res);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn = (Button) findViewById(R.id.btnSave);
        btnShow = (Button) findViewById(R.id.btnShow);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString() + "\n";
                String location = loc.getText().toString();
                String designation = desig.getText().toString();
                String spinnername = spn_res;

                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertUserDetails(username, location, designation, spinnername);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);

            }
        });
    }
}
