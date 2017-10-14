package newbaking.code.develop.bizartxo.newbakingapp.ui;


import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/15/17.
 */

public class AuxActivity extends AppCompatActivity {


    Intent intent;
    boolean novideo = false;
    ArrayList<String> ss = new ArrayList<>();
    static ArrayList<Step> actualStep;
    int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActionBar toolbar = getSupportActionBar();

        toolbar.setDisplayHomeAsUpEnabled(true);


        intent = getIntent();

        actualStep = intent.getParcelableArrayListExtra("stepO");

        Bundle bundle = intent.getBundleExtra("data");
        step = bundle.getInt("step");

        String link = actualStep.get(step).getVideoURL(); //bundle.getString("video");
        String sd = actualStep.get(step).getShortDescription(); //bundle.getString("sdesc");
        String d = actualStep.get(step).getDescription(); //bundle.getString("desc");

        ss = bundle.getStringArrayList("videos");

        Log.d("aaaaaaaaaaaaaaaa","lllll: " + link + "-" + sd + "-" + d);

        if (link.equals(""))
            novideo = true;

        if (novideo) {
            setContentView(R.layout.info_fragment);
            Bundle video = new Bundle();
            video.putString("sdesc", sd);
            video.putString("desc", d);
            video.putStringArrayList("videos",ss);
            video.putInt("step",step);
            InfoFragment fragment = new InfoFragment();

            fragment.setArguments(video);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.info_novideo, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            setContentView(R.layout.detail_recipe_fragment);
            Bundle video = new Bundle();
            video.putString("video", link);
            video.putString("sdesc", sd);
            video.putString("desc", d);
            video.putInt("step",step);
            video.putStringArrayList("videos",ss);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(video);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.video_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause(){
        RecipeDetailFragment.stopPlayer();
        super.onPause();

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
}

