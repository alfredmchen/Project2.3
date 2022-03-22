package com.example.uscrecsport;

public class Appointment {
    private String RecCenter;
    private String appointment_id;
    private String month;
    private String date;
    private String time;

    public Appointment(String recCenter, String apptid, String m, String d, String t) {
        RecCenter = recCenter;
        appointment_id = apptid;
        month = m;
        date = d;
        time = t;
    }

    public String getRecCenter() {
        return RecCenter;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
