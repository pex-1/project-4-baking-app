package udacity.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import udacity.bakingapp.fragments.FragmentDetails;

public class DetailView extends AppCompatActivity {
    private FragmentDetails fragmentDetails;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fragment", "exists");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        if(savedInstanceState == null){
            fragmentDetails = new FragmentDetails();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frame_layout, fragmentDetails)
                    .commit();

        }





    }
}
