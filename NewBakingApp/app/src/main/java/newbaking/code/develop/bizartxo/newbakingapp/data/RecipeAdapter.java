package newbaking.code.develop.bizartxo.newbakingapp.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;

/**
 * Created by izartxo on 9/12/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = ".....adapter......";

    private static final int ID_KEY = 0x01;

    List<Recipe> recipeList;
    final private OnGorkaClick mGorkaListener;
    Context _context;


    public RecipeAdapter(List<Recipe> recipes, OnGorkaClick listener, Context context){
        recipeList = recipes;
        mGorkaListener = listener;
        _context = context;
    }

    @Override
    public int getItemCount() {
        if (recipeList==null)
            return 0;
        return recipeList.size();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "ONCREATE------------");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        RecipeViewHolder rvh = new RecipeViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder recipeViewHolder, int i) {
        Log.d(TAG, "ONBIND------------");
        recipeViewHolder.recipeTitle.setText(recipeList.get(i).getTitle());

        //recipeViewHolder.recipePhoto.setImageResource(recipeList.get(i).getPhotoId());


        Glide.with(_context)
                .load(recipeList.get(i).getPhotoIdUrl())
                .into(recipeViewHolder.recipePhoto)
                .onLoadFailed(_context.getResources().getDrawable(R.mipmap.ic_launcher_round,null));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "ONATTACH------------");
        super.onAttachedToRecyclerView(recyclerView);
    }




    public  class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;

        TextView recipeTitle;
        ImageView recipePhoto;

        RecipeViewHolder(View itemView) {

            super(itemView);
            Log.d(TAG, "ONRECIPE------------");
            cv = (CardView)itemView.findViewById(R.id.card_view);

            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_title);
            recipePhoto = (ImageView)itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            String recipeid = recipeList.get(pos).getRecipeId();
            String recipeTitle = recipeList.get(pos).getTitle();
            mGorkaListener.onGorkaClick(pos, recipeid, recipeTitle);
        }
    }

    public interface OnGorkaClick{
        public void onGorkaClick(int position, String recipeid, String recipeTitle);
    }

    public void swapData(Cursor cursor/*List<Recipe> newList*/){
        //recipeList = newList;
        Log.d(".............", "Swaping");
        ArrayList<Recipe> rlist = new ArrayList<>();
        int count = cursor.getCount();
        cursor.moveToFirst();
        //while (!cursor.isLast()){
        while (count>0){
            Log.d("xxxxxxxxxxxxxxxxxxx", "/////// " + cursor.getString(1));
            Recipe r = new Recipe();
            r.setRecipeId(String.valueOf(cursor.getString(0)));
            r.setTitle(cursor.getString((1)));
            r.setPhotoIdUrl(cursor.getString(2));
            Log.d("-------------------","------->" + r.getRecipeId() + "#" + r.getTitle() + "IURL: " + r.getPhotoIdUrl());
            rlist.add(r);
            count--;
            cursor.moveToNext();
        }
        recipeList = rlist;
        this.notifyDataSetChanged();
    }


}
