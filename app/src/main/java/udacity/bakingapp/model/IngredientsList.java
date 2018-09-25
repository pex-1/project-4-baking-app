package udacity.bakingapp.model;

import java.util.ArrayList;

public class IngredientsList {

    private String recipeName;
    private ArrayList<String> ingredientsList;

    public IngredientsList(){
        ingredientsList = new ArrayList<>();
    }

    public IngredientsList(String recipeName, ArrayList<String> ingredientsList) {
        this.recipeName = recipeName;
        this.ingredientsList = ingredientsList;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(ArrayList<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
