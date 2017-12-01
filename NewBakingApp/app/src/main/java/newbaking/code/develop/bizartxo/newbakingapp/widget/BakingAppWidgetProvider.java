package newbaking.code.develop.bizartxo.newbakingapp.widget;


import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;
import newbaking.code.develop.bizartxo.newbakingapp.model.RecipeColumns;
import newbaking.code.develop.bizartxo.newbakingapp.ui.AuxActivity;
import newbaking.code.develop.bizartxo.newbakingapp.ui.RecipeMainActivity;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static final String APP_UPD = "newbaking.code.develop.bizartxo.newbakingapp.widget.APP_UPDATE";

    private static Context mContext;
    private static Cursor mData=null;
    private static int recipenum = 1;
    private static int mTotal = 0;
    private static ArrayList<Recipe> mList;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Perform this loop procedure for each App Widget that belongs to this provider
        mContext = context;
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);

            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, BakingAppWidgetService.class);
            //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.rvW,intent);

            views.setEmptyView(R.id.rvW, R.id.empty);

            /*Intent intentSync = new Intent (context, BakingAppWidgetService.class);

            intentSync.setAction((AppWidgetManager.ACTION_APPWIDGET_UPDATE));

            //intentSync.putExtra("recipenum", recipenum);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.empty, pendingIntent);*/





            Intent intentSync = new Intent (mContext, TestIntent.class);



            intentSync.putExtra("ing", String.valueOf(recipenum));
            if (!mList.isEmpty())
                Log.d("RECIPENUM", "Num:" + mList.get(recipenum).getTitle());

            //intentSync.putExtra("recipenum", recipenum);

            PendingIntent pI = PendingIntent.getService(mContext, 0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.header, pI);


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.rvW);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) || intent.getAction().equals(APP_UPD)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID), R.id.rvW);
            onUpdate(context,appWidgetManager,appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.rvW,
                new Intent(context, BakingAppWidgetService.class));
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.rvW,
                new Intent(context, BakingAppWidgetService.class));
    }


    public static void updateWidget(Cursor cursor, Context context, ArrayList<Recipe> zerrenda){
            mData = cursor;
            mList = zerrenda;
            Log.d("---------------------",">>>>>>>>>> " + mData.getCount());
            Intent intent = new Intent(context, BakingAppWidgetProvider.class);
            intent.setAction(APP_UPD);
            context.sendBroadcast(intent);
            mTotal = zerrenda.size();
            Log.d("TOTAL RECIPES", "----------------->>>>>>>" + mTotal);
        }

        public static Cursor getData(){
            return mData;
        }

    public static void setRecipenum(){
        if (recipenum>=4)
            recipenum = 1;
        else
            recipenum += 1;
    }
}