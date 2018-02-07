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
import java.util.HashMap;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static final String APP_UPD = "newbaking.code.develop.bizartxo.newbakingapp.widget.APP_UPDATE";

    private static Context mContext;
    private static Cursor mData=null;
    private static int recipenum = 1;
    private static int mTotal = 0;
    private static ArrayList<Recipe> mList;
    private static HashMap<String, String> hm;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        mContext = context;
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);

            Intent intent = new Intent(context, BakingAppWidgetService.class);

            intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.rvW,intent);

            views.setEmptyView(R.id.rvW, R.id.empty);

            Intent intentSync = new Intent (mContext, WidgetIntentService.class);

            intentSync.putExtra("ing", String.valueOf(recipenum));

            if (mList!=null && !mList.isEmpty()) {
                int local = recipenum;
                if (recipenum==1)
                    local = 1;
                Log.d("RECIPENUM", "Num:" + mList.get(local).getTitle());
                views.setTextViewText(R.id.header, mList.get(local).getTitle());

            }

            if (hm!=null && !hm.isEmpty()) {
                int local = recipenum;
                if (recipenum==1)
                    local = 1;
                views.setTextViewText(R.id.header, hm.get(String.valueOf(local)));

            }

            PendingIntent pI = PendingIntent.getService(mContext, 0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.header, pI);

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


   /* @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.rvW,
                new Intent(context, BakingAppWidgetService.class));
    }


    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.rvW,
                new Intent(context, BakingAppWidgetService.class));
    }*/


    public static void updateWidget(Cursor cursor, Context context, HashMap lhm/*ArrayList<Recipe> zerrenda*/){
            mData = cursor;

            hm = lhm;
            /*Log.d("---------------------",">>>>>>>>>> " + mData.getCount());*/
            Intent intent = new Intent(context, BakingAppWidgetProvider.class);
            intent.setAction(APP_UPD);
            context.sendBroadcast(intent);
            mTotal = hm.size(); //zerrenda.size();
            /*Log.d("TOTAL RECIPES", "----------------->>>>>>>" + mTotal);*/
        }

        public static Cursor getData(){
            return mData;
        }

    public static void setRecipeNum(){
        if (recipenum>=4)
            recipenum = 1;
        else
            recipenum += 1;
    }
}