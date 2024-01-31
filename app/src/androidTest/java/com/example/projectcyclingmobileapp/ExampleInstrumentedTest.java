package com.example.projectcyclingmobileapp;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;

import org.junit.Rule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ExampleInstrumentedTest {

    //defines a rule for the tests to use the LogIn activity class
    @Rule
    public ActivityScenarioRule<LogIn> activityRule = new ActivityScenarioRule<>(LogIn.class);

    //Tests the LogIn class with a regular user input
    @Test
    public void testLogin1() {
        // Type the email and password into the text fields
        Espresso.onView(ViewMatchers.withId(R.id.Username)).perform(ViewActions.typeText("valid_email@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.typeText("valid_password"), ViewActions.closeSoftKeyboard());

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.btn_LogInConfirm)).perform(ViewActions.click());
    }

    //Tests the LogIn class with a regular user input
    @Test
    public void testLogin2() {
        // Type invalid email and password into the text fields
        Espresso.onView(ViewMatchers.withId(R.id.Username)).perform(ViewActions.typeText("dina9316@gmail.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.btn_LogInConfirm)).perform(ViewActions.click());
    }

    //Tests the LogIn class with admin input
    @Test
    public void testAdminLogin(){
        Espresso.onView(ViewMatchers.withId(R.id.Username)).perform(ViewActions.typeText("admin"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.typeText("admin"), ViewActions.closeSoftKeyboard());

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.btn_LogInConfirm)).perform(ViewActions.click());
    }

}