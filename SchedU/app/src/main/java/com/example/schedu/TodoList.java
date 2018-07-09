package com.example.schedu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoList extends Activity {

    private ArrayList<TextView> tvList;
    private Context mContext;
    private Activity mActivity;
    private EditText edit_todo;
    private TextView show_todo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        edit_todo = (EditText) findViewById(R.id.edit_todo);
        show_todo = (TextView) findViewById(R.id.show_todo);

        String content = edit_todo.getText().toString(); //gets you the contents of edit text
        show_todo.setText(content);
    }

}
