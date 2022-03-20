package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = (EditText) findViewById(R.id.username);
        EditText passWord = (EditText) findViewById(R.id.password);
        Button loginButt = (Button) findViewById(R.id.loginbutton);
        Button newUserRegister = (Button) findViewById(R.id.registerjumpbutton);
        DBHelper db = new DBHelper(this);

        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = userName.getText().toString();
                String pw = passWord.getText().toString();
                if (db.checkusernamepassword(un,pw)) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    intent.putExtra("username",un);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUserintent = new Intent (MainActivity.this,RegisterActivity.class);
                startActivity(newUserintent);
            }
        });
    }
}