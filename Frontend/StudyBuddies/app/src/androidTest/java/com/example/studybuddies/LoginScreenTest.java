package com.example.studybuddies;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<LoginScreen> activityRule = new ActivityScenarioRule<LoginScreen>(LoginScreen.class);

    @Test
    public void usernameEditTextTest(){
        String testString = "testuser", resultString = "testuser";
        onView(withId(R.id.enter_user_name)).perform(typeText(testString)).check(matches(withText(resultString)));
    }
}
