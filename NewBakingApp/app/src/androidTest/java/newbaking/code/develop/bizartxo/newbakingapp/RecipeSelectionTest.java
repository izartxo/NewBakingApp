package newbaking.code.develop.bizartxo.newbakingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.ui.RecipeDetailActivity;
import newbaking.code.develop.bizartxo.newbakingapp.ui.RecipeMainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class RecipeSelectionTest {


    @Rule
    public ActivityTestRule<RecipeMainActivity> mActivityRule = new ActivityTestRule<>(
            RecipeMainActivity.class);

    @Test
    public void recipeSelection() throws Exception{

        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rvsteps)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));

        onView(allOf(withId(R.id.nextButton))).perform(click());
    }

    @Test
    public void recipeSelectionExact() throws Exception{

        onView(withRecyclerView(R.id.rv).atPositionOnView(2, R.id.recipe_title)).check(matches(withText("Nutella Pie")));




    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {


        return new RecyclerViewMatcher(recyclerViewId);
    }


}
