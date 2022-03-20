package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText un = (EditText) findViewById(R.id.regisusername);
        EditText pw = (EditText) findViewById(R.id.regispassword);
        Button regisButton = (Button) findViewById(R.id.registerbutton);
        Button backtoSignInButton = (Button) findViewById(R.id.backtosigninbtn);
        DBHelper db = new DBHelper(this);

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = un.getText().toString();
                String password = pw.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(RegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(db.checkusername(username)){
                        Toast.makeText(RegisterActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean last = db.insertUser(username,password);
                        if(last){
                            Toast.makeText(RegisterActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        backtoSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}