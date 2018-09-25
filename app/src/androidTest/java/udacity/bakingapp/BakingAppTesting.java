package udacity.bakingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class BakingAppTesting {
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<MainActivity>(MainActivity.class);

    /*
    @Before
    public void idlingResourcesRegister(){
        idlingResource = main.getActivity().getIdlingResources();
    }*/

    @Test
    public void clickOnRecipeOpensUpStepsView(){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredients_text_view))
                .check(matches(isDisplayed()));

    }


    @Test
    public void clickOnStepOpensDetails() throws InterruptedException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1500);
        onView(withId(R.id.exo_player))
                .check(matches(isDisplayed()));
    }



}
