package com.example.studybuddies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.not;

import android.content.ComponentName;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class CreateUserTest {
    private static final int SIMULATED_DELAY_MS = 500;


    @Rule
    public ActivityScenarioRule<CreateUser> activityRule = new ActivityScenarioRule<CreateUser>(CreateUser.class);

    @Test
    public void validEmailTest(){
        ActivityScenario<CreateUser> scenario = activityRule.getScenario();
        String username = "testuser1";
        String password1 = "password";
        String password2 = "password";
        String email = "testuser1.email.com";
        String location = "City, State";
        String errorMessage = "invalid email address";

        onView(withId(R.id.user_name)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password_one)).perform(typeText(password1), closeSoftKeyboard());
        onView(withId(R.id.password_two)).perform(typeText(password2), closeSoftKeyboard());
        onView(withId(R.id.email_address)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.location)).perform(typeText(location), closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.error_display)).check(matches(withText(errorMessage)));
    }
}
