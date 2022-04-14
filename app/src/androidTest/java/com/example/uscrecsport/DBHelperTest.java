package com.example.uscrecsport;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DBHelperTest{
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DBHelper(appContext);
    }

    @After
    public void tearDown() throws Exception {
        appContext.deleteDatabase("recCenter.db");
    }

    @Test
    public void insertUserTest(){
        assertTrue("check if successfully input user alfred", dbHelper.insertUser("alfred", "chen", "111"));
        assertTrue("check if successfully input user ethan", dbHelper.insertUser("ethan", "feng", "123"));
        assertFalse("check if error for input two ethan with different id", dbHelper.insertUser("ethan", "feng", "124"));
        assertFalse("check repeated input of user", dbHelper.insertUser("alfred", "chen", "111"));
    }

    @Test
    public void getUserTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "122");
        assertEquals("check inserted name","alfred", dbHelper.getUser("111"));
        assertEquals("check inserted name","ethan", dbHelper.getUser("122"));
        assertNull("check not existing name", dbHelper.getUser("123"));
    }

    @Test
    public void setImgUrlTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "123");
        assertTrue("check set img url with out img_url 1", dbHelper.setImageUrl("alfred", "www.img.com"));
        assertTrue("check set img url with out img_url 2", dbHelper.setImageUrl("ethan", "www.img.com"));
        assertTrue("check set img url function with user with img_url", dbHelper.setImageUrl("alfred", "www.img.com2"));
    }


    @Test
    public void getImgUrlTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "123");
        dbHelper.setImageUrl("alfred", "www.img.com");
        assertEquals("check get img url function with user","www.img.com", dbHelper.getImageUrl("alfred"));
        assertNull("check get img url function with user without set image", dbHelper.getImageUrl("ethan"));
    }

    @Test
    public void insertAppointmentTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertTrue("check insert village appointment test 1", dbHelper.insertAppointment("village", 312, "alfred"));
        assertTrue("check insert village appointment test 2", dbHelper.insertAppointment("village", 412, "alfred"));
        assertTrue("check insert lyon appointment test 1", dbHelper.insertAppointment("lyon", 312, "alfred"));
        assertTrue("check insert lyon appointment test 2", dbHelper.insertAppointment("lyon", 412, "alfred"));
        assertFalse("check insert village repeated appointment", dbHelper.insertAppointment("village", 312, "alfred"));
        assertFalse("check insert lyon repeated appointment", dbHelper.insertAppointment("lyon", 412, "alfred"));
    }

    @Test
    public void insertWaitlistTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertTrue("check insert village waitlist test 1", dbHelper.insertWaitlist("village", 312, "alfred"));
        assertTrue("check insert village waitlist test 2", dbHelper.insertWaitlist("village", 412, "alfred"));
        assertTrue("check insert lyon waitlist test 1", dbHelper.insertWaitlist("lyon", 312, "alfred"));
        assertTrue("check insert lyon waitlist test 2", dbHelper.insertWaitlist("lyon", 412, "alfred"));
        assertFalse("check insert village repeated waitlist", dbHelper.insertWaitlist("village", 312, "alfred"));
        assertFalse("check insert lyon repeated waitlist", dbHelper.insertWaitlist("lyon", 412, "alfred"));
    }

    @Test
    public void getAppointmentIdTest(){
        assertEquals("check march village appointment id 1", "95", dbHelper.getAppointmentId("village", "3", "12", "20").toString());
        assertEquals("check march village appointment id 2", "63", dbHelper.getAppointmentId("village", "3", "8", "20").toString());
        assertEquals("check april village appointment id 1", "343", dbHelper.getAppointmentId("village", "4", "12", "20").toString());
        assertEquals("check april village appointment id 2", "311", dbHelper.getAppointmentId("village", "4", "8", "20").toString());
        assertEquals("check may village appointment id 1", "591", dbHelper.getAppointmentId("village", "5", "12", "20").toString());
        assertEquals("check may village appointment id 2", "559", dbHelper.getAppointmentId("village", "5", "8", "20").toString());
        assertEquals("check march lyon appointment id 1", "95", dbHelper.getAppointmentId("lyon", "3", "12", "20").toString());
        assertEquals("check march lyon appointment id 2", "63", dbHelper.getAppointmentId("lyon", "3", "8", "20").toString());
        assertEquals("check april lyon appointment id 1", "343", dbHelper.getAppointmentId("lyon", "4", "12", "20").toString());
        assertEquals("check april lyon appointment id 2", "311", dbHelper.getAppointmentId("lyon", "4", "8", "20").toString());
        assertEquals("check may lyon appointment id 1", "591", dbHelper.getAppointmentId("lyon", "5", "12", "20").toString());
        assertEquals("check may lyon appointment id 2", "559", dbHelper.getAppointmentId("lyon", "5", "8", "20").toString());
    }

    @Test
    public void getTimeTest(){
        assertEquals("check village appointment time", "4", dbHelper.getTime("village", 284).get(0));
        assertEquals("check village appointment time", "5", dbHelper.getTime("village", 284).get(1));
        assertEquals("check village appointment time", "14", dbHelper.getTime("village", 284).get(2));
        assertEquals("check lyon appointment time", "3", dbHelper.getTime("lyon", 189).get(0));
        assertEquals("check lyon appointment time", "24", dbHelper.getTime("lyon", 189).get(1));
        assertEquals("check lyon appointment time", "16", dbHelper.getTime("lyon", 189).get(2));
    }

    @Test
    public void checkAppointmentAvailabilityTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "123");
        dbHelper.insertUser("brenden", "lee", "222");
        assertTrue("check village appointment availability with none", dbHelper.checkAppointmentAvailability("village", "5", "8","20"));

        dbHelper.insertAppointment("village", 559, "alfred");
        assertTrue("check village appointment availability with one", dbHelper.checkAppointmentAvailability("village", "5", "8","20"));

        dbHelper.insertAppointment("village", 559, "ethan");
        assertTrue("check village appointment availability with two", dbHelper.checkAppointmentAvailability("village", "5", "8","20"));

        dbHelper.insertAppointment("village", 559, "brenden");
        assertFalse("check village appointment availability with three", dbHelper.checkAppointmentAvailability("village", "5", "8","20"));

        assertTrue("check lyon appointment availability with none", dbHelper.checkAppointmentAvailability("lyon", "3", "12","20"));

        dbHelper.insertAppointment("lyon", 95, "alfred");
        assertTrue("check lyon appointment availability with one", dbHelper.checkAppointmentAvailability("lyon", "3", "12","20"));

        dbHelper.insertAppointment("lyon", 95, "ethan");
        assertTrue("check lyon appointment availability with two", dbHelper.checkAppointmentAvailability("lyon", "3", "12","20"));

        dbHelper.insertAppointment("lyon", 95, "brenden");
        assertFalse("check lyon appointment availability with three", dbHelper.checkAppointmentAvailability("lyon", "3", "12","20"));
    }

    @Test
    public void checkAppointmentTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertFalse("check if no appointment return false", dbHelper.checkAppointment("village", 189, "alfred"));
        dbHelper.insertAppointment("village", 189, "alfred");
        assertTrue("check if appointment exists return true", dbHelper.checkAppointment("village", 189, "alfred"));

        assertFalse("check if no appointment return false", dbHelper.checkAppointment("lyon", 200, "alfred"));
        dbHelper.insertAppointment("lyon", 200, "alfred");
        assertTrue("check if appointment exists return true", dbHelper.checkAppointment("lyon", 200, "alfred"));
    }

    @Test
    public void checkWaitlistTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertFalse("check if no appointment return false", dbHelper.checkWaitlist("village", 189, "alfred"));
        dbHelper.insertWaitlist("village", 189, "alfred");
        assertTrue("check if appointment exists return true", dbHelper.checkWaitlist("village", 189, "alfred"));

        assertFalse("check if no appointment return false", dbHelper.checkWaitlist("lyon", 200, "alfred"));
        dbHelper.insertWaitlist("lyon", 200, "alfred");
        assertTrue("check if appointment exists return true", dbHelper.checkWaitlist("lyon", 200, "alfred"));
    }

    @Test
    public void checkusernamepasswordTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "222");
        assertTrue("check if username matches with password", dbHelper.checkusernamepassword("alfred", "chen"));
        assertTrue("check if username matches with password", dbHelper.checkusernamepassword("ethan", "feng"));
        assertFalse("check if username does not match with password", dbHelper.checkusernamepassword("alfred", "che"));
        assertFalse("check if username does not match with password", dbHelper.checkusernamepassword("ethan", "chen"));
        assertFalse("check if username does not exist", dbHelper.checkusernamepassword("brenden", "chen"));
        assertFalse("check if username does not exist", dbHelper.checkusernamepassword("brenden", "feng"));
    }

    @Test
    public void getPastAppointmentsTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertTrue("check no appointments", dbHelper.getPastAppointments("alfred", 3, 25, 22).isEmpty());
        dbHelper.insertAppointment("village", 222, "alfred");
        dbHelper.insertAppointment("lyon", 223, "alfred");
        assertTrue("check no past appointments", dbHelper.getPastAppointments("alfred", 3, 25, 22).isEmpty());
        dbHelper.insertAppointment("village", 100, "alfred");
        dbHelper.insertAppointment("lyon", 90, "alfred");
        assertEquals("check past appointments", "Village gym", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(1).getRecCenter());
        assertEquals("check past appointments", "100", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(1).getAppointment_id());
        assertEquals("check past appointments", "3", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(1).getMonth());
        assertEquals("check past appointments", "13", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(1).getDate());
        assertEquals("check past appointments", "14", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(1).getTime());
        assertEquals("check past appointments", "Lyon Center", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(0).getRecCenter());
        assertEquals("check past appointments", "90", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(0).getAppointment_id());
        assertEquals("check past appointments", "3", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(0).getMonth());
        assertEquals("check past appointments", "12", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(0).getDate());
        assertEquals("check past appointments", "10", dbHelper.getPastAppointments("alfred", 3, 25, 22).get(0).getTime());
    }

    @Test
    public void getCurrentAppointmentsTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertTrue("check no appointments", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).isEmpty());
        dbHelper.insertAppointment("village", 100, "alfred");
        dbHelper.insertAppointment("lyon", 90, "alfred");
        assertTrue("check no current appointments", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).isEmpty());
        dbHelper.insertAppointment("village", 222, "alfred");
        dbHelper.insertAppointment("lyon", 223, "alfred");
        assertEquals("check current appointments", "Village gym", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(1).getRecCenter());
        assertEquals("check current appointments", "222", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(1).getAppointment_id());
        assertEquals("check current appointments", "3", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(1).getMonth());
        assertEquals("check current appointments", "28", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(1).getDate());
        assertEquals("check current appointments", "18", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(1).getTime());
        assertEquals("check current appointments", "Lyon Center", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(0).getRecCenter());
        assertEquals("check current appointments", "223", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(0).getAppointment_id());
        assertEquals("check current appointments", "3", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(0).getMonth());
        assertEquals("check current appointments", "28", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(0).getDate());
        assertEquals("check current appointments", "20", dbHelper.getCurrentAppointments("alfred", 3, 25, 22).get(0).getTime());
    }

    @Test
    public void insertNotificationTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        assertTrue("check insert village notification test 1", dbHelper.insertAppointment("village", 312, "alfred"));
        assertTrue("check insert village notification test 2", dbHelper.insertAppointment("village", 412, "alfred"));
        assertTrue("check insert lyon notification test 1", dbHelper.insertAppointment("lyon", 312, "alfred"));
        assertTrue("check insert lyon notification test 2", dbHelper.insertAppointment("lyon", 412, "alfred"));
        assertFalse("check insert village repeated notification", dbHelper.insertAppointment("village", 312, "alfred"));
        assertFalse("check insert lyon repeated notification", dbHelper.insertAppointment("lyon", 412, "alfred"));
    }

    @Test
    public void moveWaitlistToNotificationListTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertWaitlist("village", 312, "alfred");
        dbHelper.insertWaitlist("village", 412, "alfred");
        dbHelper.insertWaitlist("lyon", 312, "alfred");
        dbHelper.insertWaitlist("lyon", 412, "alfred");
        dbHelper.moveWaitlistToNotificationList("Village gym", 312);
        dbHelper.moveWaitlistToNotificationList("Village gym", 412);
        dbHelper.moveWaitlistToNotificationList("lyon", 312);
        dbHelper.moveWaitlistToNotificationList("lyon", 412);
        List<Appointment> test = dbHelper.getNotification("alfred");
        assertEquals("check move village waitlist to notification list test 1", "312", test.get(0).getAppointment_id());
        assertEquals("check move village waitlist to notification list test 1", "village", test.get(0).getRecCenter());
        assertEquals("check move village waitlist to notification list test 2", "412", test.get(1).getAppointment_id());
        assertEquals("check move village waitlist to notification list test 2", "village", test.get(1).getRecCenter());
        assertEquals("check move lyon waitlist to notification list test 1", "312", test.get(2).getAppointment_id());
        assertEquals("check move lyon waitlist to notification list test 1", "lyon", test.get(2).getRecCenter());
        assertEquals("check move lyon waitlist to notification list test 2", "412", test.get(3).getAppointment_id());
        assertEquals("check move lyon waitlist to notification list test 2", "lyon", test.get(3).getRecCenter());
    }

    @Test
    public void deleteWaitlistTest(){
        dbHelper.insertUser("alfred", "chen", "111");
        dbHelper.insertUser("ethan", "feng", "112");
        dbHelper.insertWaitlist("village", 312, "alfred");
        dbHelper.insertWaitlist("village", 312, "ethan");
        assertFalse("check delete empty waitlist test ", dbHelper.insertWaitlist("village", 312, "alfred"));
        dbHelper.deleteWaitlist("Village gym", 312);
        assertTrue("check delete waitlist test 1", dbHelper.insertWaitlist("village", 312, "alfred"));
        dbHelper.deleteWaitlist("Village gym", 312);
        dbHelper.deleteWaitlist("Village gym", 312);
        assertTrue("check delete waitlist test 2", dbHelper.insertWaitlist("village", 312, "alfred"));
    }

    @Test
    public void cancelAppointmentTest(){
        dbHelper.insertUser("kwan", "pass", "208");
        assertFalse("check if appointment is cancelled", dbHelper.checkAppointment("lyon", 513, "kwan"));
        dbHelper.insertAppointment("lyon", 513, "kwan");
        assertTrue("check if appointment is inserted", dbHelper.checkAppointment("lyon", 513, "kwan"));
        dbHelper.cancelAppointment("lyon", 513, "kwan");
        assertFalse("check if appointment is cancelled", dbHelper.checkAppointment("lyon", 513, "kwan"));
    }

    @Test
    public void checkusernameTest(){
        assertFalse("check if user exists already", dbHelper.checkusername("kwan"));
        dbHelper.insertUser("kwan", "pass", "208");
        assertTrue("check if user exists now", dbHelper.checkusername("kwan"));
    }
}