package newbaking.code.develop.bizartxo.newbakingapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    static boolean twoPane;
    static String recipeTitle;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
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
            Fragment fragmentList = new RecipeListFragment();

            FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();

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


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.master_list_fragment, fragmentList);
            //transaction.addToBackStack("master_list_fragment");
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
}

