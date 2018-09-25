package udacity.bakingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.bakingapp.model.Recipe;

public interface Api {
    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
