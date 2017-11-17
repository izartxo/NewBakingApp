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

import android.view.MenuItem;
import android.widget.Toolbar;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    static boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_recipe_detail);

        ActionBar toolbar = getSupportActionBar();

        toolbar.setDisplayHomeAsUpEnabled(true);



        if (findViewById(R.id.port) != null){
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
        //setContentView(R.layout.activity_recipe_detail);
        if (twoPane){
            Fragment fragment = new RecipeListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.master_list_fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Fragment fragment2 = new RecipeDetailFragment();

            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();

            transaction2.add(R.id.video_frame, fragment2);
            transaction2.addToBackStack(null);
            transaction2.commit();
        }
        else{
            Fragment fragment = new RecipeListFragment();


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.master_list_fragment, fragment);
            transaction.addToBackStack("master_list_fragment");
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

