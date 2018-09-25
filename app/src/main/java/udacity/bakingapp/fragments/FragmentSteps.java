package udacity.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.bakingapp.AdapterItemClickListener;
import udacity.bakingapp.R;
import udacity.bakingapp.adapters.StepsAdapter;
import udacity.bakingapp.model.Recipe;
import udacity.bakingapp.model.Steps;

public class FragmentSteps extends Fragment {
    private TextView ingredientsTextView;

    private StepsPositionListener listener;

    //recycler view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public int position;
    private Recipe recipe;


    //mandatory empty constructor
    public FragmentSteps(){
    }

    //position interface
    public interface StepsPositionListener{
        void positionChange(int position);
    }


    public void positionChange(int position){
        this.position = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){

            listener.positionChange(savedInstanceState.getInt("position"));
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        recipe = getActivity().getIntent().getParcelableExtra("recipe");



        //Recipe recipe = getArguments().getParcelable("recipe");
        ingredientsTextView = view.findViewById(R.id.ingredients_text_view);

        for(int i = 0; i<recipe.getIngredients().size(); i++){
            if(i == recipe.getIngredients().size()-1){
                ingredientsTextView.append("-" + recipe.getIngredients().get(i).getIngredient() + " (" + recipe.getIngredients().get(i).getQuantity() + " "
                        + recipe.getIngredients().get(i).getMeasure() + ")");
            }else
                ingredientsTextView.append("-" + recipe.getIngredients().get(i).getIngredient() + " (" + recipe.getIngredients().get(i).getQuantity() + " "
                        + recipe.getIngredients().get(i).getMeasure() + ")\n");
        }

        ArrayList<Steps> steps = (ArrayList<Steps>) recipe.getSteps();

        setRecyclerView(steps, view, getActivity());

        /*
        if(savedInstanceState != null){

            listener.positionChange(savedInstanceState.getInt("position"));
        }
        */


        return view;
    }



    private void setRecyclerView(ArrayList<Steps> steps, View view, Context context) {
        recyclerView = view.findViewById(R.id.steps_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        adapter = new StepsAdapter(steps, context, new AdapterItemClickListener() {


            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_SHORT).show();
                positionChange(position);
                listener.positionChange(position);

            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof StepsPositionListener){
            listener = (StepsPositionListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement StepsPositionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
