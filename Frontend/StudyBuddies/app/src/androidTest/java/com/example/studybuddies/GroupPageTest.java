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
import static org.hamcrest.Matchers.containsString;
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
public class GroupPageTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Dashboard> activityRule = new ActivityScenarioRule<Dashboard>(Dashboard.class);

    @Before
    public void enterTheGroupPage(){
        onView(withText(containsString("ID: 44"))).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void toGroupMembersPageNavigationTest(){
        onView(withId(R.id.members_button)).perform(click());
        onView(withId(R.id.groupMembersRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void toGroupPostsPageNavigationTest(){
        onView(withId(R.id.posts_button)).perform(click());
        onView(withId(R.id.group_posts_scroller)).check(matches(isDisplayed()));
    }
}
