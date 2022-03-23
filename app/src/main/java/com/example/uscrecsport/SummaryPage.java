package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class SummaryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);

        String username = getIntent().getStringExtra("username");
        Calendar cal = Calendar.getInstance();
        int currmonth = cal.get(Calendar.MONTH) + 1;
        int currday = cal.get(Calendar.DAY_OF_MONTH);
        int currhour = cal.get(Calendar.HOUR_OF_DAY);
        DBHelper db = new DBHelper(this);
        List<Appointment> resultCurrentAppt = db.getCurrentAppointments(username,currmonth,currday,currhour);
        List<Appointment> resultPastAppt = db.getPastAppointments(username,currmonth,currday,currhour);

        LinearLayout current_appointment_list = (LinearLayout) findViewById(R.id.current_appointments_list);
        if(resultCurrentAppt.isEmpty()){
            TextView text = new TextView(this);
            text.setText("No Current Appointment");
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            current_appointment_list.addView(text);
        }else {
            for (Appointment appt: resultCurrentAppt) {
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                TextView text = new TextView(this);
                Button button = new Button(this);

                String temp = "";
                temp += (appt.getRecCenter() + ": " + appt.getMonth() + "/" + appt.getDate() + " " + appt.getTime() + ":00 \n");
                text.setText(temp);
                text.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.1f
                ));
                button.setText("X");
                button.setBackgroundColor(Color.RED);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        current_appointment_list.removeView(layout);
                        //delete appointment in db and send notifications if required
                        db.cancelAppointment(appt.getRecCenter(), db.getAppointmentId(appt.getRecCenter(), appt.getMonth(),
                                appt.getDate(), appt.getTime()), username);
                    }
                });
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                layout.addView(text);
                layout.addView(button);

                current_appointment_list.addView(layout);
            }
        }

        LinearLayout past_appointment_list = (LinearLayout) findViewById(R.id.past_appointments_list);
        if(resultCurrentAppt.isEmpty()){
            TextView text = new TextView(this);
            text.setText("No Past Appointment");
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            past_appointment_list.addView(text);
        }else {
            for (Appointment appt: resultPastAppt) {
                TextView text = new TextView(this);
                String temp = "";
                temp += (appt.getRecCenter() + ": " + appt.getMonth() + "/" + appt.getDate() + " " + appt.getTime() + ":00 \n");
                text.setText(temp);
                text.setText(temp);
                text.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                past_appointment_list.addView(text);
            }
        }
    }

}