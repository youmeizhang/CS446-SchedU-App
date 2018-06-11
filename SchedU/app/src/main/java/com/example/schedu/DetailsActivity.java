package com.example.schedu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<HashMap<String, String>> userList;
    ListAdapter adapter;
    ListView lv;
    DbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        lv = (ListView) findViewById(R.id.user_list);
        //DbHandler db = new DbHandler(this);
        db = new DbHandler(this);
        userList = db.GetUsers();
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, userList, R.layout.list_row, new String[]{"id", "subject", "course", "session", "priority"}, new int[]{R.id.lr_id, R.id.lr_subject, R.id.lr_course, R.id.lr_session, R.id.lr_priority});
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.lr_id);
                int num = Integer.valueOf(textView.getText().toString());
                System.out.println("Position & id & num is " + position + id + num);
                removeItemFromList(position, id, num);
                return true;
            }
        });

        Button back = (Button) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    protected void removeItemFromList(int position, final long id, final int num) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(DetailsActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this course?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userList.remove(deletePosition);
                db.DeleteUser(num);
                ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

}













