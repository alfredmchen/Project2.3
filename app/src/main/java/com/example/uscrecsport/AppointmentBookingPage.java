package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentBookingPage extends AppCompatActivity{

    DBHelper dbHelperRegister = new DBHelper(AppointmentBookingPage.this);
    TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking_page);
        Intent temp = getIntent();
        String gymName = "";
        String username = "";
        String apptyear = "";
        String apptmonth ="";
        String apptdate = "";
        if(temp.getStringExtra("username") != null){
            username = temp.getStringExtra("username");
        }
        if(temp.getStringExtra("gymName") != null){
            gymName = temp.getStringExtra("gymName");
        }
        if(temp.getStringExtra("year") != null){
            apptyear = temp.getStringExtra("year");
        }
        if(temp.getStringExtra("month") != null){
            apptmonth = temp.getStringExtra("month");
        }
        if(temp.getStringExtra("date") != null){
            apptdate = temp.getStringExtra("date");
        }
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(apptmonth + " / " + apptdate + " Appointment Times");

        LinearLayout appointment_list = (LinearLayout) findViewById(R.id.appointment_list);
        for(int i=8; i<24; i+=2){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            TextView text = new TextView(this);
            Button button = new Button(this);

            text.setText("         " + i + ":00 - " +(i+2) + ":00");
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            String finalGymName = gymName;
            String finalApptmonth = apptmonth;
            String finalApptdate = apptdate;
            int finalI = i;
            String finalUsername = username;

            if(dbHelperRegister.checkAppointmentAvailability(gymName, apptmonth, apptdate, String.valueOf(i))){
                button.setText("Book");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(dbHelperRegister.insertAppointment(finalGymName, dbHelperRegister.getAppointmentId(finalGymName, finalApptmonth,
                                finalApptdate, String.valueOf(finalI)), finalUsername)){
                            Toast.makeText(AppointmentBookingPage.this, "successfully booked", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AppointmentBookingPage.this, "booking already exists", Toast.LENGTH_SHORT).show();
                        }
                        if(!dbHelperRegister.checkAppointmentAvailability(finalGymName, finalApptmonth, finalApptdate, String.valueOf(finalI))) {
                            button.setText("Remind Me");
                            button.setBackgroundColor(Color.RED);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(dbHelperRegister.insertWaitlist(finalGymName, dbHelperRegister.getAppointmentId(finalGymName, finalApptmonth,
                                            finalApptdate, String.valueOf(finalI)), finalUsername)) {
                                        Toast.makeText(AppointmentBookingPage.this, "successfully put on waitlist", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(AppointmentBookingPage.this, "already on waitlist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
            }else{
                button.setText("Remind Me");
                button.setBackgroundColor(Color.RED);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(dbHelperRegister.insertWaitlist(finalGymName, dbHelperRegister.getAppointmentId(finalGymName, finalApptmonth,
                                finalApptdate, String.valueOf(finalI)), finalUsername)) {
                            Toast.makeText(AppointmentBookingPage.this, "successfully put on waitlist", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AppointmentBookingPage.this, "already on waitlist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.8f
            ));
            button.setId(i);
            layout.addView(text);
            layout.addView(button);

            appointment_list.addView(layout);
        }

    }
}