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

import com.google.android.exoplayer2.util.Util;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.ui.RecipeDetailFragment;
import newbaking.code.develop.bizartxo.newbakingapp.ui.RecipeListFragment;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    static boolean twoPane;
    static String recipeTitle;

    static boolean isVideoActivity = false;


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

        }
        else{
            twoPane = false;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

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




        if (twoPane){

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeListFragment fragmentList = null;

            if (fragmentManager.findFragmentById(R.id.master_list_fragment) == null){
                fragmentList = new RecipeListFragment();

            }else{
                fragmentList = (RecipeListFragment) fragmentManager.findFragmentById(R.id.master_list_fragment);
            }



            FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();



            transactionList.replace(R.id.master_list_fragment, fragmentList);
            transactionList.addToBackStack(null);
            transactionList.commit();


            RecipeDetailFragment fragmentDetail = null;

            if (fragmentManager.findFragmentById(R.id.video_frame) == null){
                fragmentDetail = new RecipeDetailFragment();

            }else{
                fragmentDetail = (RecipeDetailFragment) fragmentManager.findFragmentById(R.id.video_frame);
            }



            FragmentTransaction transactionDetail = getSupportFragmentManager().beginTransaction();

            transactionDetail.replace(R.id.video_frame, fragmentDetail);
            transactionDetail.addToBackStack(null);
            transactionDetail.commit();
        }
        else{

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeListFragment fragmentList = null;

            if (fragmentManager.findFragmentById(R.id.master_list_fragment) == null){
                fragmentList = new RecipeListFragment();


            }else{
                fragmentList = (RecipeListFragment) fragmentManager.findFragmentById(R.id.master_list_fragment);
            }


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.master_list_fragment, fragmentList);

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



    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 && !isVideoActivity){
            RecipeDetailFragment.stopPlayer();
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        if (Util.SDK_INT > 23 && !isVideoActivity){
            RecipeDetailFragment.stopPlayer();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static void setIsVideoActivity(boolean state){
        isVideoActivity = state;
    }


}

