package newbaking.code.develop.bizartxo.newbakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.Ingredient;
import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;

/**
 * Created by bizartxo on 17/10/17.
 */

public class BakingAppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        return new RemoteViewsFactory() {

            private Cursor cursor = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null){
                    cursor.close();
                }

                Uri bakingWidgetUri = RecipeProvider.Ingredients.INGREDIENTS;
                cursor = getContentResolver().query(bakingWidgetUri,
                        null,
                        null,
                        null,
                        null);
            }

            @Override
            public void onDestroy() {
                if (cursor != null){
                    cursor.close();
                    cursor = null;
                }
            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                if (i == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(i)){
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_item);

                String ingredient = cursor.getString(1);

                views.setTextViewText(R.id.ingredientW, ingredient);

                return views;

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
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    /*@Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetFactory(this.getApplicationContext(), intent);
    }

    class BakingWidgetFactory implements RemoteViewsFactory{

        private Context mContext;
        private Cursor mCursor;
        private int mAppWidgetId;

        BakingWidgetFactory(Context context, Intent intent){
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            onDataSetChanged();
        }

        @Override
        public void onDestroy() {
            if (mCursor != null) {
                mCursor.close();
            }
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            // Get the data for this position from the content provider
            String ingredient = "----";

            if (mCursor.moveToPosition(i)) {
                final int ingredientIndex = mCursor.getColumnIndex(IngredientColumns.INGREDIENT);

                ingredient = mCursor.getString(ingredientIndex);
            }

            // Return a proper item with the proper day and temperature

            final int itemId = R.layout.ingredient_item;
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), itemId);
            rv.setTextViewText(R.id.ingredient, ingredient);
            //rv.setScrollPosition(R.id.weather_list, 5);

            // Set the click intent so that we can handle it and show a toast message


            return rv;
            //--
            *//*RemoteViews row=new RemoteViews(ctx.getPackageName(),
                    R.layout.ingredient_item);

            mIngredientCursor.moveToPosition(i);

            String ing = mIngredientCursor.getString(mIngredientCursor.getColumnIndex(IngredientColumns.INGREDIENT));
            row.setTextViewText(R.id.ingredient, "hhhhhhhhh");

            Intent intent = new Intent();
            Bundle extras=new Bundle();

            extras.putString("gorka", ingredientList.get(i).getIngredient());
            intent.putExtras(extras);


            return(row);*//*
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        public long getItemId(int position) {
            return position;
        }

        public boolean hasStableIds() {
            return true;
        }

        public void onDataSetChanged() {
            // Refresh the cursor
            if (mCursor != null) {
                mCursor.close();
            }
            mCursor = mContext.getContentResolver().query(RecipeProvider.Ingredients.INGREDIENTS, null, null,
                    null, null);
        }
    }*/

}
