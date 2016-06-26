package com.excalibur.mytodos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextId, editTextPassword;
    Button btnLogin;
    SharedPreferences sharedPref;
    String packageName = "com.excalibur.mytodos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextPassword = (EditText) findViewById(R.id.editTextPw);
        btnLogin = (Button) findViewById(R.id.btnNextPage);

        sharedPref = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
        //id 라는 키가 존재하면 가져와라 없으면 빈 칸으로 셋팅하자
        String id = sharedPref.getString("id", "");
        String pw = sharedPref.getString("pw", "");
        editTextId.setText(id);
        editTextPassword.setText(pw);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String correct_id = "erica";
                String correct_pw = "kang";
                String id = editTextId.getText().toString();
                String pw = editTextPassword.getText().toString();

                if(!id.equals(correct_id)) {
                    Toast.makeText(getApplicationContext(), "Wrong ID!", Toast.LENGTH_SHORT).show();

                } else if (!pw.equals(correct_pw)) {
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();

                } else {
                    loginSuccess(correct_id, correct_pw);

                }
            }
        });
     }
     public void loginSuccess(String correct_id, String correct_pw) {
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString("id", correct_id);
         editor.putString("pw", correct_pw);
         editor.commit();

         Activity fromActivity = MainActivity.this;
         Class toActivity = TodoActivity.class;
         Intent intent = new Intent(fromActivity, toActivity);
         intent.putExtra("id", correct_id);
         startActivity (intent);
           finish();
     }
}
