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



    List<Recipe> mRecipeList;
    final private OnRecipeClick mRecipeListener;
    Context _context;


    public RecipeAdapter(List<Recipe> recipes, OnRecipeClick listener, Context context){
        mRecipeList = recipes;
        mRecipeListener = listener;
        _context = context;
    }

    @Override
    public int getItemCount() {
        if (mRecipeList==null)
            return 0;
        return mRecipeList.size();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        RecipeViewHolder rvh = new RecipeViewHolder(view);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.recipeTitle.setText(mRecipeList.get(position).getTitle());

        Glide.with(_context)
                .load(mRecipeList.get(position).getPhotoIdUrl())
                .into(recipeViewHolder.recipePhoto)
                .onLoadFailed(_context.getResources().getDrawable(R.mipmap.ic_launcher_round,null));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }


    public  class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;

        TextView recipeTitle;
        ImageView recipePhoto;

        RecipeViewHolder(View itemView) {

            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);

            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_title);
            recipePhoto = (ImageView)itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            String recipeId = mRecipeList.get(pos).getRecipeId();
            String recipeTitle = mRecipeList.get(pos).getTitle();
            mRecipeListener.onRecipeClick(pos, recipeId, recipeTitle);
        }
    }

    public interface OnRecipeClick{
        void onRecipeClick(int position, String recipeId, String recipeTitle);
    }

    public void swapData(Cursor cursor){

        ArrayList<Recipe> recipeList = new ArrayList<>();

        int count = cursor.getCount();
        cursor.moveToFirst();

        while (count>0){

            Recipe recipe = new Recipe();
            recipe.setRecipeId(String.valueOf(cursor.getString(0)));
            recipe.setTitle(cursor.getString((1)));
            recipe.setPhotoIdUrl(cursor.getString(2));

            recipeList.add(recipe);
            count--;
            cursor.moveToNext();
        }
        mRecipeList = recipeList;

        this.notifyDataSetChanged();
    }


}
