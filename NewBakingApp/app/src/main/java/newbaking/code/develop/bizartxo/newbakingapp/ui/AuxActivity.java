package newbaking.code.develop.bizartxo.newbakingapp.ui;


import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/15/17.
 */

public class AuxActivity extends AppCompatActivity {


    Intent intent;
    boolean noVideo = false;
    ArrayList<String> videosList = new ArrayList<>();
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

        String link = actualStep.get(step).getVideoURL();
        String sd = actualStep.get(step).getShortDescription();
        String d = actualStep.get(step).getDescription();

        videosList = bundle.getStringArrayList("videos");

        toolbar.setTitle(sd);

        if (TextUtils.isEmpty(link))
            noVideo = true;

        if (noVideo) {
            setContentView(R.layout.info_fragment);
            Bundle video = new Bundle();
            video.putString("sdesc", sd);
            video.putString("desc", d);
            video.putStringArrayList("videos", videosList);
            video.putInt("step",step);

            FragmentManager fragmentManager = getSupportFragmentManager();

            InfoFragment fragment = null;

            if (fragmentManager.findFragmentById(R.id.info_novideo) == null){
                fragment = new InfoFragment();
                fragment.setArguments(video);
            }else{
                fragment = (InfoFragment) fragmentManager.findFragmentById(R.id.info_novideo);
            }



            // To avoid Espresso ambiguous problem
            ViewGroup vgParent = (ViewGroup) findViewById(R.id.d_r_f);
            if (vgParent!=null)
                vgParent.removeView(findViewById(R.id.nextButton));
            //

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.info_novideo, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            setContentView(R.layout.detail_recipe_fragment);
            Bundle video = new Bundle();
            video.putString("video", link);
            video.putString("sdesc", sd);
            video.putString("desc", d);
            video.putInt("step",step);
            video.putStringArrayList("videos", videosList);

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeDetailFragment fragment = null;

            if (fragmentManager.findFragmentById(R.id.video_frame) == null){
                fragment = new RecipeDetailFragment();
                fragment.setArguments(video);
            }else{
                fragment = (RecipeDetailFragment) fragmentManager.findFragmentById(R.id.video_frame);
            }


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.video_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // To avoid Espresso ambiguous problem
            ViewGroup vgParent = (ViewGroup) findViewById(R.id.d_r_f);
            if (vgParent!=null)
                vgParent.removeView(findViewById(R.id.nextButton));
            //


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();

    }

    @Override
    protected void onResume(){

        super.onResume();
        RecipeDetailActivity.setIsVideoActivity(true);
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onDestroy(){
        RecipeDetailActivity.setIsVideoActivity(false);
        super.onDestroy();

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
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(".........................", ">>>>>>>>> " + savedInstanceState.getInt("orientation"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("orientation", getResources().getConfiguration().orientation);

    }

    @Override
    public void onStart(){
        super.onStart();

    }


}



