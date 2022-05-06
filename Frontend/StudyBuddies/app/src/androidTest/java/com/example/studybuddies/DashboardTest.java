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
public class DashboardTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Dashboard> activityRule = new ActivityScenarioRule<Dashboard>(Dashboard.class);

    @Test
    public void CreateGroupButtonTest(){
        onView(withId(R.id.create_group)).perform(click());
        onView(withId(R.id.group_creation_textview)).check(matches(isDisplayed()));
    }

    @Test
    public void fromDashboardToProfilePageTest(){
        navigateTo(R.id.nav_profile);
        onView(withId(R.id.profilePicImageView)).check(matches(isDisplayed()));
    }

    @Test
    public void fromDashboardToPublicGroupsPageTest(){
        navigateTo(R.id.nav_publicGroups);
        onView(withId(R.id.searchViewGroupSearch)).check(matches(isDisplayed()));
    }

    @Test
    public void fromDashboardToSchedulePageTest(){
        navigateTo(R.id.nav_schedule);
        onView(withId(R.id.addNewEventButton)).check(matches(isDisplayed()));
    }

    public void navigateTo(int id){
        onView(withId(R.id.base_drawer)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(id));
    }
}
