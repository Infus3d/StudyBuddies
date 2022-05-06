package com.example.studybuddies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.not;

import android.content.ComponentName;
import android.view.Gravity;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.navigation.NavigationView;

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

    @Test
    public void invalidUsernameOrPasswordTest(){
        String username = "testuser";
        String password = "password123";
        String errorMessage = "Invalid username or password. Try again";

        onView(withId(R.id.enter_user_name)).perform(typeText(username));
        onView(withId(R.id.enter_password)).perform(typeText(password));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.login_error)).check(matches(withText(errorMessage)));
    }

    @Test
    public void validUsernameOrPasswordTest(){
        String username = "testusername";
        String password = "password";
        String errorMessage = "";

        onView(withId(R.id.enter_user_name)).perform(typeText(username));
        onView(withId(R.id.enter_password)).perform(typeText(password));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.login_error)).check(doesNotExist());
        logOut();
    }

    @Test
    public void validUsernameAndPasswordTestIntentToDashboard(){
        String username = "testusername";
        String password = "password";
        String errorMessage = "";

        onView(withId(R.id.enter_user_name)).perform(typeText(username));
        onView(withId(R.id.enter_password)).perform(typeText(password));
        onView(withId(R.id.login_button)).perform(click());
//        intended(hasComponent(new ComponentName(getTargetContext(), ExpectedActivity.class)));


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

//        assertEquals("Dashboard", InstrumentationRegistry.getInstrumentation().getTargetContext().getClass().getSimpleName());
        onView(withId(R.id.create_group)).check(matches(isDisplayed()));
//        onView(withId(R.id.base_drawer)).perform(click());
        logOut();
    }

    void logOut(){
        onView(withId(R.id.base_drawer)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));
    }
}
