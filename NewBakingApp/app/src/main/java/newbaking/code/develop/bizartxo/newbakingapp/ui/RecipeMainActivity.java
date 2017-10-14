package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;
import newbaking.code.develop.bizartxo.newbakingapp.network.HttpAsyncTask;
import newbaking.code.develop.bizartxo.newbakingapp.network.NetworksUtils;

public class RecipeMainActivity extends AppCompatActivity implements RecipeAdapter.OnGorkaClick, LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "----------------------------";

    private static final int ID_BAKING_LOADER = 44;
    private static final int ID_INGREDIENT_LOADER = 55;
    private static final int ID_STEP_LOADER = 66;

    static RecyclerView rv;
    List<Recipe> recipes;
    static RecipeAdapter adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_recipe_main);

        if (findViewById(R.id.grid_normal) == null) // begiratu landscape tablet
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        context = getApplicationContext();

        boolean isLarge = getString(R.string.size).equals("small")?false:true;
        //Log.d("-------------",":::---> " + getString(R.string.size) + "#" + isLarge);
        rv = (RecyclerView)findViewById(R.id.rv);

        int columns = isLarge?3:1;

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), columns);
        rv.setLayoutManager(glm);

        adapter = new RecipeAdapter(recipes, this, context);
        rv.setAdapter(adapter);

        rv.setHasFixedSize(true);

        adapter.notifyDataSetChanged();

        // Cursor recipes = getContentResolver().query(MyContentProvider.Recipes.RECIPES, null, null, null, null);



        getSupportLoaderManager().initLoader(ID_BAKING_LOADER, null, this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onGorkaClick(int position, String recipeid) {
        Toast.makeText(this, "GORKA: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("RID", recipeid);
        startActivity(intent);

    }

    public static void refreshData(List<Recipe> r){
        //  adapter.swapData(r);
    }

    //////////////////////////// LOADER LOGIC //////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_BAKING_LOADER:



                return new CursorLoader(this, RecipeProvider.Recipes.RECIPES, null, null, null, null);

            case ID_INGREDIENT_LOADER:


                return new CursorLoader(this, RecipeProvider.Ingredients.INGREDIENTS, null, args.getString("selection"), args.getStringArray("selectionargs"), null);

            case ID_STEP_LOADER:


                return new CursorLoader(this, RecipeProvider.Steps.STEPS, null, args.getString("selection"), args.getStringArray("selectionArgs"), null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data==null || data.getCount()==0){
            Log.d("---------","Cargar datos...");
            if (NetworksUtils.isConnected(context)){
                try {
                    HttpAsyncTask asyncMovieData = new HttpAsyncTask(getApplicationContext());
                    asyncMovieData.execute(new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"));
                }
                catch(IOException ioe){
                    Log.d(TAG, "ERROOOOOOOR");
                }
            }else{
                NetworksUtils.showMessage(RecipeMainActivity.this, "Internet not available.");
            }
        }
        else {
            Log.d("---------", "Zenbat: " + data.getCount());
            adapter.swapData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "LoadReset............");
    }

    ///////////////////////// LOADER LOGIC END //////////////////////////
}
