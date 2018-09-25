package udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.bakingapp.R;
import udacity.bakingapp.StepsView;
import udacity.bakingapp.fragments.FragmentSteps;
import udacity.bakingapp.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private Context context;


    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView recipeName;
        private TextView servings;
        private ImageView imageView;
        private RelativeLayout parentLayout;

        public RecipeViewHolder(View itemView){
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            servings = itemView.findViewById(R.id.servings);
            imageView = itemView.findViewById(R.id.bell);
            parentLayout = itemView.findViewById(R.id.parentLayoutRecipe);
        }

    }

    public RecipeAdapter(List<Recipe> recipes, Context context){
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        //RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.servings.append(recipe.getServings() + "");

        if(recipe.getImageURL() != null){
            Picasso.with(context)
                    .load(recipe.getImageURL())
                    .resize(50, 50)
                    .into(holder.imageView);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepsView.class);

                //passing recipe to fragment activity
                /*
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", recipe);
                FragmentSteps fragmentSteps = new FragmentSteps();
                fragmentSteps.setArguments(bundle);
                */

                intent.putExtra("recipe", recipe);
                context.startActivity(intent);
                //Toast.makeText(context, recipe.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
