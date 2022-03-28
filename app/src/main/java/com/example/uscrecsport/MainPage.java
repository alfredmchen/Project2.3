package com.example.uscrecsport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String username = getIntent().getStringExtra("username");
        Button lyon = findViewById(R.id.lyon_button);
        Button village = findViewById(R.id.village_button);
        Button summaryPage = findViewById(R.id.summarypagebutton);


        TextView welcome = (TextView)findViewById(R.id.welcomeusertextview);
        TextView currentAppt = (TextView)findViewById(R.id.currentAppointmentTextView);
        DBHelper db = new DBHelper(this);
        Calendar cal = Calendar.getInstance();
        int currmonth = cal.get(Calendar.MONTH) + 1;
        int currday = cal.get(Calendar.DAY_OF_MONTH);
        int currhour = cal.get(Calendar.HOUR_OF_DAY);



        List<Appointment> resultCurrentAppt = db.getCurrentAppointments(username,currmonth,currday,currhour);
        String resultCurAppt = "Current Appointments: \n";
        if(resultCurrentAppt.isEmpty()){
            resultCurAppt += "No current appointments";
        }
        for(int i = 0; i < resultCurrentAppt.size();i++){
            String temp = "";
            temp += (resultCurrentAppt.get(i).getRecCenter() + ": " + resultCurrentAppt.get(i).getMonth() + "/" + resultCurrentAppt.get(i).getDate() + " " + resultCurrentAppt.get(i).getTime() + ":00 \n");
            resultCurAppt += temp;
        }
        currentAppt.setText(resultCurAppt);
        currentAppt.setMovementMethod(new ScrollingMovementMethod());
        String welcometxt = "Welcome " + username + "\n" + Integer.toString(currmonth) + "/" + Integer.toString(currday) + " " + Integer.toString(currhour) + ":00";
        welcome.setText(welcometxt);



        lyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","lyon");
                intent.putExtra("username",username);
                intent.putExtra("currmonth", currmonth);
                intent.putExtra("currday", currday);
                intent.putExtra("currhour", currhour);
                startActivity(intent);
            }
        });

        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","village");
                intent.putExtra("username",username);
                intent.putExtra("currmonth", currmonth);
                intent.putExtra("currday", currday);
                intent.putExtra("currhour", currhour);
                startActivity(intent);
            }
        });

        summaryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, SummaryPage.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String username = getIntent().getStringExtra("username");
        Calendar cal = Calendar.getInstance();
        int currmonth = cal.get(Calendar.MONTH) + 1;
        int currday = cal.get(Calendar.DAY_OF_MONTH);
        int currhour = cal.get(Calendar.HOUR_OF_DAY);

        TextView currentAppt = (TextView)findViewById(R.id.currentAppointmentTextView);
        List<Appointment> resultCurrentAppt = db.getCurrentAppointments(username, currmonth, currday,currhour);
        String resultCurAppt = "Current Appointments: \n";
        if(resultCurrentAppt.isEmpty()){
            resultCurAppt += "No current appointments";
        }
        for(int i = 0; i < resultCurrentAppt.size();i++){
            String temp = "";
            temp += (resultCurrentAppt.get(i).getRecCenter() + ": " + resultCurrentAppt.get(i).getMonth() + "/" + resultCurrentAppt.get(i).getDate() + " " + resultCurrentAppt.get(i).getTime() + ":00 \n");
            resultCurAppt += temp;
        }
        currentAppt.setText(resultCurAppt);
        currentAppt.setMovementMethod(new ScrollingMovementMethod());

        TextView notification = (TextView)findViewById(R.id.waitlistview);
        List<Appointment> notilist = db.getNotification(username);
        String resultnotification = "Notification \n";
        for(Appointment a : notilist){
            String temp = "";
            temp += (a.getRecCenter() + ": " + a.getMonth() + "/" + a.getDate() + " " + a.getTime()+ ":00 is avaliable now");
            resultnotification += temp;
        }
        notification.setText(resultnotification);
        notification.setMovementMethod(new ScrollingMovementMethod());

        TextView welcome = (TextView)findViewById(R.id.welcomeusertextview);
        String welcometxt = "Welcome " + username + "\n" + Integer.toString(currmonth) + "/" + Integer.toString(currday) + " " + Integer.toString(currhour) + ":00";
        welcome.setText(welcometxt);

        Button setprofilepic = findViewById(R.id.setpicbtn);
        ImageView pfp = findViewById(R.id.profilepic);
        String urltemp = db.getImageUrl(username);
        if(urltemp == null){
            pfp.setImageResource(R.drawable.avatar);
        }else{
            Glide.with(this).load(urltemp).into(pfp);
        }
        boolean success = false;
        setprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog  = new AlertDialog.Builder(MainPage.this);
                dialog.setTitle("Enter picture url: ");
                final EditText picurl = new EditText(MainPage.this);
                picurl.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.setView(picurl);

                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String picurltemp = picurl.getText().toString();
                        if(!picurltemp.isEmpty()) {
                            db.setImageUrl(username, picurltemp);
                        }
                    }
                });
                dialog.show();
            }
        });



    }
}