package com.example.uscrecsport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
        TextView currentAppt = findViewById(R.id.currentAppointmentTextView);
        welcome.setText("Welcome " + username);
        currentAppt.setMovementMethod(new ScrollingMovementMethod());
        lyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","lyon");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","village");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
}