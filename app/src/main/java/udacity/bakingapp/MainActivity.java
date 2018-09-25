package udacity.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import udacity.bakingapp.adapters.RecipeAdapter;
import udacity.bakingapp.model.Recipe;
import udacity.bakingapp.utils.NetworkUtils;
import udacity.bakingapp.utils.RetrofitUtils;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int COLUMNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getResources().getBoolean(R.bool.isTablet) == true){
            COLUMNS = 3;
        }else COLUMNS = 1;

        if(NetworkUtils.isNetworkConnected(getApplicationContext())){
            getJson();
        }else Toast.makeText(this, "No network connection!", Toast.LENGTH_SHORT).show();
        
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    public void getJson(){
        Retrofit retrofit = RetrofitUtils.getClient();

        Api api = retrofit.create(Api.class);

        Call<List<Recipe>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                final List<Recipe> recipes = response.body();
                setUpRecyclerView(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpRecyclerView(List<Recipe> recipes){
        recyclerView = findViewById(R.id.recycler_recipes);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecipeAdapter(recipes, MainActivity.this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        recyclerView.setAdapter(adapter);

    }
}

/*
Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

intent.putExtra(getString(R.string.movieObject), movie);
 */