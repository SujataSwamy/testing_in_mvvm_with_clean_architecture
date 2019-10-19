package com.example.myapplication;

import androidx.test.espresso.IdlingRegistry;

import com.example.myapplication.activity.BookActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(JUnit4.class)
public class BookScreenUITest {


    @Rule
    public ActivityTestRule<BookActivity> activityRule
            = new ActivityTestRule<>(BookActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void test()  {
        onView(withId(R.id.spinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Author1")))
                .perform(click());

        onView(withId(R.id.spinner))
                .check(matches(withSpinnerText("Author1")));
    }

}
