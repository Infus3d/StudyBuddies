package com.example.studybuddies;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
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

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAssertion;
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
public class CreateGroupTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<CreateGroup> activityRule = new ActivityScenarioRule<CreateGroup>(CreateGroup.class);

    @Test
    public void groupCreationInputTest(){
        String groupName = "New JUnit test";
        String description = "Junit test new description";
        onView(withId(R.id.group_name_edit_text)).perform(typeText(groupName), closeSoftKeyboard()).check(matches(withText(groupName)));
        onView(withId(R.id.group_description)).perform(typeText(description), closeSoftKeyboard()).check(matches(withText(description)));
    }

    @Test
    public void groupCreationTest(){
        String groupName = "New JUnit test";
        String description = "Junit test new description";
        onView(withId(R.id.group_name_edit_text)).perform(typeText(groupName), closeSoftKeyboard());
        onView(withId(R.id.group_description)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.is_public_switch)).perform(click(), click());
        onView(withId(R.id.create_group_button)).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.welcome_container)).check(matches(isDisplayed()));
    }
}
