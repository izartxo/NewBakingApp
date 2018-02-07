package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;


import java.io.IOException;
import java.net.URL;
import java.util.List;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;
import newbaking.code.develop.bizartxo.newbakingapp.network.HttpAsyncTask;
import newbaking.code.develop.bizartxo.newbakingapp.network.NetworksUtils;
import newbaking.code.develop.bizartxo.newbakingapp.widget.WidgetIntentService;

public class RecipeMainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClick, LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "-----------RECIPE MAIN ACTIVITY----------";

    public static final int REQUEST_CODE = 10;

    private static final int ID_BAKING_LOADER = 44;
    private static final int ID_INGREDIENT_LOADER = 55;
    private static final int ID_STEP_LOADER = 66;

    static RecyclerView rv;
    List<Recipe> recipes;
    static RecipeAdapter adapter;
    Context context;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        activity = this;
        context = getApplicationContext();

        setContentView(R.layout.activity_recipe_main);

        if (findViewById(R.id.grid_normal) == null)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        boolean isLarge = getString(R.string.size).equals("small")?false:true;

        int columns = isLarge?3:1;


        rv = (RecyclerView)findViewById(R.id.rv);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), columns);
        rv.setLayoutManager(glm);

        adapter = new RecipeAdapter(recipes, this, context);
        rv.setAdapter(adapter);

        rv.setHasFixedSize(true);

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onRecipeClick(int position, String recipeid, String recipetitle) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("RID", recipeid);
        intent.putExtra("TITLE", recipetitle);
        startActivity(intent);

    }

    @Override
    public void onResume(){
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), WidgetIntentService.class);
        startService(intent);
        getSupportLoaderManager().initLoader(ID_BAKING_LOADER, null, this);


    }

    //////////////////////////// LOADER LOGIC //////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_BAKING_LOADER:

                return new CursorLoader(this, RecipeProvider.Recipes.RECIPES, null, null, null, null);


            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data==null || data.getCount()==0){

            if (NetworksUtils.isConnected(context)){
                try {
                    HttpAsyncTask asyncMovieData = new HttpAsyncTask(getApplicationContext());
                    asyncMovieData.execute(new URL(NetworksUtils.RECIPES_URL));
                }
                catch(IOException ioe){
                    Log.d(TAG, "Load finished exception.....");
                }
            }else{
                NetworksUtils.showMessage(activity, "Internet not available.");
            }
        }
        else {
            adapter.swapData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "LoadReset............");
    }

    ///////////////////////// LOADER LOGIC END //////////////////////////




}
