package udacity.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import udacity.bakingapp.R;
import udacity.bakingapp.model.IngredientsList;

public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private IngredientsList ingredientsList;

    public RemoteViewsFactory(Context context){
        this.mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);

        //String val = preference.getString("recipeWidget", "null");
        String val = preference.getString("widgetData", "null");


        Gson gson = new Gson();
        ingredientsList = gson.fromJson(val, IngredientsList.class);
        //Log.e("Data: ", val);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList.getIngredientsList().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        remoteViews.setTextViewText(R.id.ingredient_item_text, ingredientsList.getIngredientsList().get(i));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
