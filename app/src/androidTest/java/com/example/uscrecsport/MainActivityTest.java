package com.example.uscrecsport;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.ViewAction;
import static org.hamcrest.CoreMatchers.allOf;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper dbHelper;
    @Rule
    public ActivityTestRule<MainActivity> intentsTestRuleA = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception{
        dbHelper = new DBHelper(appContext);
        dbHelper.insertUser("one","one","111");
    }
    @After
    public void tearDown() throws Exception{
        appContext.deleteDatabase("recCenter.db");
    }

    @Test
    public void LoginscreenSetUpTest(){
        //Test for the correct screen displayed
        onView(withText("RecSports Sign In")).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.registerjumpbutton)).check(matches(isDisplayed()));
        onView(withId(R.id.loginbutton)).check(matches(isDisplayed()));
    }

    @Test
    public void LoginscreenFailedToastTest(){
        //Test for the correct screen displayed
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withText("Failed")).inRoot(withDecorView(not(intentsTestRuleA.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }



    @Test
    public void registerNewUserButtonTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.registerjumpbutton)).perform(click());
        intended(hasComponent(hasShortClassName(".RegisterActivity")));
        Intents.release();
    }

    @Test
    public void registerNewUserSetUpTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.registerjumpbutton)).perform(click());
        onView(withId(R.id.signin)).check(matches(isDisplayed()));
        onView(withId(R.id.regisusername)).check(matches(isDisplayed()));
        onView(withId(R.id.regispassword)).check(matches(isDisplayed()));
        onView(withId(R.id.regisstudentid)).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void registerOldUserToastTest(){
        //Test for the correct screen displayed
        onView(withId(R.id.registerjumpbutton)).perform(click());
        onView(withId(R.id.regisusername)).perform(typeText("one"));
        onView(withId(R.id.regispassword)).perform(typeText("one"));
        onView(withId(R.id.regisstudentid)).perform(typeText("111"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withText("Username already exist")).inRoot(withDecorView(not(intentsTestRuleA.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void registerWithNoInfoToastTest(){
        //Test for the correct screen displayed
        onView(withId(R.id.registerjumpbutton)).perform(click());
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withText("Enter all fields")).inRoot(withDecorView(not(intentsTestRuleA.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void registerPageLoginBtnTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.registerjumpbutton)).perform(click());
        onView(withId(R.id.backtosigninbtn)).perform(click());
        intended(hasComponent(hasShortClassName(".MainActivity")));
        Intents.release();
    }

    @Test
    public void logInUserTest(){
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        intended(hasComponent(hasShortClassName(".MainPage")));
        Intents.release();
    }

    @Test
    public void MainPageSetUpTest(){
        //Test for the correct screen displayed
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.waitlistview)).check(matches(isDisplayed()));
        onView(withId(R.id.lyon_button)).check(matches(isDisplayed()));
        onView(withId(R.id.village_button)).check(matches(isDisplayed()));
        onView(withId(R.id.currentAppointmentTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.setpicbtn)).check(matches(isDisplayed()));
        onView(withId(R.id.profilepic)).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void profilePicbtnTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.setpicbtn)).perform(click());
        onView(withText("Enter picture url: ")).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void LyonCenterBtnTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.lyon_button)).perform(click());
        intended(hasComponent(hasShortClassName(".BookingPage")));
        Intents.release();
    }

    @Test
    public void LyonCenterBookingPageSetUpTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.lyon_button)).perform(click());
        onView(withText("Lyon Gym Booking Page")).check(matches(isDisplayed()));
        Intents.release();
    }


    @Test
    public void VillageGymBookingPageSetUpTest(){
        //Testing intent passing from main activity to
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.village_button)).perform(click());
        onView(withText("Village Gym Booking Page")).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void VillageGymBtnTest(){
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.village_button)).perform(click());
        intended(hasComponent(hasShortClassName(".BookingPage")));
        Intents.release();
    }

    @Test
    public void summaryPageBtnTest(){
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.summarypagebutton)).perform(click());
        intended(hasComponent(hasShortClassName(".SummaryPage")));
        Intents.release();
    }

    @Test
    public void summaryPageSetUpTest(){
        Intents.init();
        onView(withId(R.id.username)).perform(typeText("one"));
        onView(withId(R.id.password)).perform(typeText("one"));
        closeSoftKeyboard();
        onView(withId(R.id.loginbutton)).perform(click());
        onView(withId(R.id.summarypagebutton)).perform(click());
        onView(withId(R.id.currentApptTextviewSummaryPage)).check(matches(isDisplayed()));
        onView(withId(R.id.pastApptTextviewSummaryPage)).check(matches(isDisplayed()));
        onView(withId(R.id.summary_page_title)).check(matches(isDisplayed()));
        Intents.release();
    }








}
