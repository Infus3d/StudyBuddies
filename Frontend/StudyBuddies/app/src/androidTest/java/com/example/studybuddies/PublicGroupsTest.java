package com.example.studybuddies;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import android.content.ComponentName;
import android.view.Gravity;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.navigation.NavigationView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PublicGroupsTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Dashboard> activityRule = new ActivityScenarioRule<Dashboard>(Dashboard.class);

    @Before
    public void enterTheGroupPage(){
        navigateTo(R.id.nav_publicGroups);
    }

    public void navigateTo(int id){
        onView(withId(R.id.base_drawer)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(id));
    }

    @Test
    public void publicGroupsPageContentTest(){
        int spinnerId = R.id.spinnerSortBy;
        int searchId = R.id.searchViewGroupSearch;
        String typeText = "SE";
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(searchId)).perform(click()).check(matches(isDisplayed()));
        onView(withId(spinnerId)).check(matches(isDisplayed()));
    }

    @Test
    public void publicGroupsPageSpinnerTest(){
        int spinnerId = R.id.spinnerSortBy;
        String selectionText = "Group Name";
        onView(withId(spinnerId)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(selectionText))).perform(click());
        onView(withId(spinnerId)).check(matches(withSpinnerText(containsString(selectionText))));
    }
}
