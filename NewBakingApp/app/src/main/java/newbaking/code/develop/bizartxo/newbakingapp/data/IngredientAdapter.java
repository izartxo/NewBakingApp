package newbaking.code.develop.bizartxo.newbakingapp.data;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Ingredient;
import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/13/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements RemoteViewsService.RemoteViewsFactory {

    String[] datos = new String[3];
    ArrayList<Ingredient> ingredientList;
    Cursor mIngredientCursor;

    private Context ctx = null;
    private int appWidgetId;

    public IngredientAdapter(Context context, Intent intent){
        ctx = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    public IngredientAdapter(){


    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_item, viewGroup, false);
        IngredientAdapter.IngredientViewHolder rvh = new IngredientAdapter.IngredientViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder recipeViewHolder, int i) {
        //recipeViewHolder.ting.setText("\u2022  " + ingredientList.get(i).getIngredient());

        if (!mIngredientCursor.isClosed()){
            mIngredientCursor.moveToPosition(i);



            String ingredientId = String.valueOf(mIngredientCursor.getString(0));
            String quantity = String.valueOf(mIngredientCursor.getString(2));
            String ingredient = String.valueOf(mIngredientCursor.getString(1));

            Ingredient mIng = new Ingredient();

            mIng.setRid(ingredientId);
            mIng.setIngredient(ingredient);
            mIng.setQuantity(quantity);

            recipeViewHolder.ting.setText("\u2022  " + mIng.getIngredient());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        /*if (ingredientList==null)
            return 0;
        else
            return ingredientList.size();*/
        if (mIngredientCursor==null)
            return 0;
        else
            return mIngredientCursor.getCount();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row=new RemoteViews(ctx.getPackageName(),
                R.layout.ingredient_item);

        mIngredientCursor.moveToPosition(i);

        String ing = mIngredientCursor.getString(mIngredientCursor.getColumnIndex(IngredientColumns.INGREDIENT));
        row.setTextViewText(R.id.ingredient, "hhhhhhhhh");

        Intent intent = new Intent();
        Bundle extras=new Bundle();

        extras.putString("gorka", ingredientList.get(i).getIngredient());
        intent.putExtras(extras);


        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{

        TextView ting;

        IngredientViewHolder(View v){
            super(v);
            ting = (TextView) v.findViewById(R.id.ingredient);
        }
    }

    public void swapDataIngredient(Cursor cursor/*, Recipe recipe*/){

       /* while (cursor.moveToNext()){
            Step newstep = new Step();
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++){

                Log.d("Logging.............", "Steps: " + cursor.getString(i));

            }
            // Log.d("RiiD", "Rid: " + cursor3.getString(0));
            newstep.setRid(cursor.getString(0));
            newstep.set_id(cursor.getInt(1));
            newstep.setShortDescription(cursor.getString(2));
            newstep.setDescription(cursor.getString(3));
            newstep.setVideoURL(cursor.getString(4));
            newstep.setThumbnailURL(cursor.getString(5));
            Log.d("-------------step-----" , ":::::::> " + newstep.getRid() + "-" + newstep.get_id() +
                    "-" + newstep.getThumbnailURL() +
                    "-" + newstep.getVideoURL());
            recipe.setStep(newstep);
        }

        mStepDataCursor =
        */

        mIngredientCursor = cursor;

        notifyDataSetChanged();
    }

}

