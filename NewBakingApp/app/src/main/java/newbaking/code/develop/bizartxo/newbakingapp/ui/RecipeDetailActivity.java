package newbaking.code.develop.bizartxo.newbakingapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    static boolean twoPane;
    static String recipeTitle;
    static int stepPosition = 0;
    static int ingPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        ActionBar toolbar = getSupportActionBar();

        toolbar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        recipeTitle = intent.getStringExtra("TITLE");

        toolbar.setTitle(recipeTitle);

        if (getString(R.string.size).equals("large")){
            twoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            twoPane = false;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ingPos", ingPosition);
        outState.putInt("stepPos", stepPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ingPosition = savedInstanceState.getInt("ingPos");
        stepPosition = savedInstanceState.getInt("stepPos");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = new Bundle();
        bundle.putInt("ingPos", ingPosition);
        bundle.putInt("stepPos", stepPosition);


        if (twoPane){
            Fragment fragmentList = new RecipeListFragment();

            FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();

            fragmentList.setArguments(bundle);

            transactionList.add(R.id.master_list_fragment, fragmentList);
            transactionList.addToBackStack(null);
            transactionList.commit();

            Fragment fragmentDetail = new RecipeDetailFragment();

            FragmentTransaction transactionDetail = getSupportFragmentManager().beginTransaction();

            transactionDetail.add(R.id.video_frame, fragmentDetail);
            transactionDetail.addToBackStack(null);
            transactionDetail.commit();
        }
        else{
            Fragment fragmentList = new RecipeListFragment();

            fragmentList.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.master_list_fragment, fragmentList);

            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public static boolean getTwoPane(){
        return twoPane;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static void setRvPositions(int iPos, int sPos){
        ingPosition = iPos;
        stepPosition = sPos;

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

