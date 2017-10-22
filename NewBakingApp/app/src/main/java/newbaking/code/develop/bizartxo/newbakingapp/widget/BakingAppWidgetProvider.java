package newbaking.code.develop.bizartxo.newbakingapp.widget;


import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.util.Random;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.ui.AuxActivity;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       // final int count = appWidgetIds.length;

        for (int appWidgetId : appWidgetIds){
        //for (int i = 0; i < count; i++) {
         //   int widgetId = appWidgetIds[i];


            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.appwidget_layout);
            views.setTextViewText(R.id.headerW, "xxxxxxx");

           // remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);

            // Update the header to reflect the weather for "today"
            /*Cursor c = context.getContentResolver().query(RecipeProvider.Ingredients.INGREDIENTS, null,
                    null, null, null);
            if (c.moveToPosition(0)) {
                int ingredientIndex = c.getColumnIndex(IngredientColumns.INGREDIENT);

                String ingredient = c.getString(ingredientIndex);

                remoteViews.setTextViewText(R.id.ingredient, ingredient);

            }
            c.close();*/


            //Intent intent = new Intent(context, BakingAppWidgetService.class);
            //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                setRemoteAdapter(context, views);
            else
                setRemoteAdapterV11(context, views);

            views.setEmptyView(R.id.rvW, R.id.empty);


            /*final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, BakingAppWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.ingredient);*/

       /*     PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
            //remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);//widgetId, remoteViews);
        }

        //super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Perform this loop procedure for each App Widget that belongs to this provider
        /*for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, AuxActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }*/
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull final Intent intent){
        super.onReceive(context, intent);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, RemoteViews views){
        views.setRemoteAdapter(R.id.rvW, new Intent(context, BakingAppWidgetService.class));
    }

    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views){
        views.setRemoteAdapter(0, R.id.rvW, new Intent(context, BakingAppWidgetService.class));
    }


}