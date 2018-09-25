package udacity.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import udacity.bakingapp.fragments.FragmentDetails;
import udacity.bakingapp.fragments.FragmentSteps;
import udacity.bakingapp.model.IngredientsList;
import udacity.bakingapp.model.Recipe;
import udacity.bakingapp.model.Steps;
import udacity.bakingapp.widget.AppWidgetService;

public class StepsView extends AppCompatActivity implements FragmentSteps.StepsPositionListener{
    private FragmentDetails fragmentDetail;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_view);

        /*
        portrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? true: false;
        */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(findViewById(R.id.two_pane_layout) != null){
            mTwoPane = true;

            fragmentDetail = new FragmentDetails();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragmentDetail)
                    .commit();
        }else {
            mTwoPane = false;
        }


    }

    @Override
    public void positionChange(int position) {
        //fragmentDetail.updatePosition(position);

        if(mTwoPane){
            FragmentDetails fragment = new FragmentDetails();
            fragment.updatePosition(position);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }else {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Steps step = recipe.getSteps().get(position);
            Intent intent = new Intent(this, DetailView.class);

            intent.putExtra("step", step);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add_widget){
            ArrayList<String> ingredientsList = new ArrayList<>();
            Recipe recipe = getIntent().getParcelableExtra("recipe");

            for(int i = 0; i < recipe.getIngredients().size(); i++){
                ingredientsList.add(recipe.getIngredients().get(i).getIngredient());
            }

            IngredientsList recipeIngredients = new IngredientsList(recipe.getName(), ingredientsList);
            String json = new Gson().toJson(recipeIngredients);

            //Log.e("Json string: ", json);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("widgetData", json);
            editor.commit();


            AppWidgetService.updateWidget(this);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }




        /*
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);

            String val = preference.getString("widgetData", "null");

            Gson gson = new Gson();
            IngredientsList ingredientsList = gson.fromJson(entry.getValue().toString(), IngredientsList.class);
         */

        return true;
    }
}
