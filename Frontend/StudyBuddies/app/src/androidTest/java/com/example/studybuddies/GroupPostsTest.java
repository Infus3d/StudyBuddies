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
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import android.content.ComponentName;
import android.view.Gravity;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(AndroidJUnit4.class)
public class GroupPostsTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Dashboard> activityRule = new ActivityScenarioRule<Dashboard>(Dashboard.class);

    @Before
    public void enterTheGroupPostsPage(){
        onView(withText(startsWith("ID: 44"))).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.posts_button)).perform(click());
    }

    @Test
    public void groupPostsPageContentTest(){
        onView(withId(R.id.create_post)).check(matches(isDisplayed()));
        onView(withId(R.id.refresh_button)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void groupPostsPageCreateAndCancelTest(){
        String newPostText = "This is brand new post with Junit testing";
        onView(withId(R.id.create_post)).perform(click());
        onView(withClassName(containsString(EditText.class.getSimpleName()))).check(matches(isDisplayed())).perform(typeText(newPostText));
        onView(allOf(withText("Cancel"))).perform(click());
        onView(withId(R.id.refresh_button)).check(matches(isDisplayed()));
    }

    @Test
    public void groupPostsPageCreateTest(){
        String newPostText = "This is brand new post with Junit testing";
        onView(withId(R.id.create_post)).perform(click());
        onView(withClassName(containsString(EditText.class.getSimpleName()))).check(matches(isDisplayed())).perform(typeText(newPostText));
        onView(allOf(withText("Post"))).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        onView(allOf(withChild(withText(containsString(newPostText))), withChild(withText(containsString("" + dtf.format(now)))))).check(matches(isDisplayed()))
        .perform(click());
        onView(withId(R.id.view_post_title)).check(matches(isDisplayed()));
        onView(withId(R.id.view_post_message)).check(matches(isDisplayed()));
    }
}
