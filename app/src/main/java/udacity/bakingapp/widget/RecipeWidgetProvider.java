package udacity.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import udacity.bakingapp.MainActivity;
import udacity.bakingapp.R;
import udacity.bakingapp.model.IngredientsList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);

        String val = preference.getString("widgetData", "null");
        //Log.e("Loading preferences", val);
        if(!val.equals("null")){
            Gson gson = new Gson();
            IngredientsList ingredientsList = gson.fromJson(val, IngredientsList.class);
            //open main activity on click
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class),0);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

            remoteViews.setTextViewText(R.id.recipe_widget_name_text, ingredientsList.getRecipeName());

            remoteViews.setOnClickPendingIntent(R.id.recipe_widget_name_text, pendingIntent);

            //list view init
            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            //bind remote adapter
            remoteViews.setRemoteAdapter(R.id.recipe_widget_listview, intent);
            //instruct widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_listview);

        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

