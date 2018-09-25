package udacity.bakingapp;

import android.support.test.espresso.IdlingResource;

public class TestingResources implements IdlingResource {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isIdleNow() {
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

    }
}
