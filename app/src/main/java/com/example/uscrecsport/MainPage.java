package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String username = getIntent().getStringExtra("username");
        Button lyon = findViewById(R.id.lyon_button);
        Button village = findViewById(R.id.village_button);
        TextView welcome = findViewById(R.id.welcomeusertextview);
        welcome.setText("Welcome " + username);
        lyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, VillageBookingPage.class);
                intent.putExtra("gymName","Lyon Center");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,VillageBookingPage.class);
                intent.putExtra("gymName","Village Gym");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
}