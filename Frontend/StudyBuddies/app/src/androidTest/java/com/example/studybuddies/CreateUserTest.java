package com.example.studybuddies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
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

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class CreateUserTest {
    private static final int SIMULATED_DELAY_MS = 500;


    @Rule
    public ActivityScenarioRule<CreateUser> activityRule = new ActivityScenarioRule<CreateUser>(CreateUser.class);

    @Test
    public void invalidEmailTest(){
        try{
            onView(withId(R.id.create_button)).check(matches(isDisplayed()));
        }
        catch (NoMatchingViewException e) {
            logOut();
        }
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

        login();
    }

    @Test
    public void validEmailTest(){
        try{
            onView(withId(R.id.create_button)).check(matches(isDisplayed()));
        }
        catch (NoMatchingViewException e) {
            logOut();
        }
        String username = "testuser7";
        String password1 = "password";
        String password2 = "password";
        String email = "testuser7@email.com";
        String location = "City, State";
        String errorMessage = "invalid email address";

        onView(withId(R.id.user_name)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password_one)).perform(typeText(password1), closeSoftKeyboard());
        onView(withId(R.id.password_two)).perform(typeText(password2), closeSoftKeyboard());
        onView(withId(R.id.email_address)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.location)).perform(typeText(location), closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_group)).check(matches(isDisplayed()));
    }

    void login(){
        onView(withId(R.id.login)).perform(click());
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
        onView(withId(R.id.create_group)).check(matches(isDisplayed()));
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
