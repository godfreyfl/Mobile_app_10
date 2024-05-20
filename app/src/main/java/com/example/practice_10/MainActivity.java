package com.example.practice_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("myPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button button_set_username = findViewById(R.id.button_set);
        Button button_get_username = findViewById(R.id.button_get);
        Button button_remove_username = findViewById(R.id.button_delete);
        Button button_next = findViewById(R.id.button4);

        EditText editText_set = findViewById(R.id.editTextText);
        TextView textView_set = findViewById(R.id.textView1);

        button_set_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editText_set.getText().toString();
                if(username.matches("")) {
                    editor.putString("username", "Guest");
                } else {
                    editor.putString("username", username);
                }

                editor.apply();

            }
        });

        button_get_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_set.setText(sharedPreferences.getString("username", "Not logged in"));
            }
        });

        button_remove_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.contains("username")) {
                    editor.remove("username");
                    editor.clear();
                    editor.apply();
                }
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewActivity.class);
                startActivity(intent);
            }
        });

    }
}