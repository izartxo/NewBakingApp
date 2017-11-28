package newbaking.code.develop.bizartxo.newbakingapp.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.Ingredient;
import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.RecipeColumns;

/**
 * Created by bizartxo on 17/10/17.
 */

public class BakingAppWidgetService extends RemoteViewsService {

    //String[] STOCK_COLUMNS = {RecipeColumns._ID,RecipeColumns.TITLE, RecipeColumns.IMAGE};

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext(), intent);
    }

    class WidgetFactory implements RemoteViewsService.RemoteViewsFactory{

            private Cursor data = null;
            private Context mContext;
            private int mAppWidgetId;

            public WidgetFactory(Context context, Intent intent){
                mContext = context;
                mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {


                data = BakingAppWidgetProvider.getData();

                if (data == null) {
                    return;
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                //final long identityToken = Binder.clearCallingIdentity();

                //Uri weatherForLocationUri = RecipeProvider.Recipes.RECIPES;
                /*data = getContentResolver().query(weatherForLocationUri,
                        STOCK_COLUMNS,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);*/

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_item);

                for (int u = 0; u < data.getColumnCount(); u++){
                    Log.d("##################", "-->> " + data.getString(u));
                    String title = data.getString(data.getColumnIndex(IngredientColumns.INGREDIENT));


                    views.setTextViewText(R.id.ingredientW, title.toUpperCase());
                }



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    // setRemoteContentDescription(views, description);
                }



                final Intent fillInIntent = new Intent();

                Uri weatherUri = RecipeProvider.Recipes.RECIPES;
                fillInIntent.setData(weatherUri);
                views.setOnClickFillInIntent(R.id.ingredientW, fillInIntent);
                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                //      views.setContentDescription(R.id.widget_icon, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(0);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
 }