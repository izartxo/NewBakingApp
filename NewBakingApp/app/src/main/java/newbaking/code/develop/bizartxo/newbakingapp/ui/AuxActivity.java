package newbaking.code.develop.bizartxo.newbakingapp.ui;


import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/15/17.
 */

public class AuxActivity extends AppCompatActivity {


    Intent intent;
    boolean novideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        intent = getIntent();

        Bundle bundle = intent.getBundleExtra("data");

        String link = bundle.getString("video");
        String sd = bundle.getString("sdesc");
        String d = bundle.getString("desc");

        Log.d("aaaaaaaaaaaaaaaa","lllll: " + link + "-" + sd + "-" + d);

        if (link.equals(""))
            novideo = true;

        if (novideo) {
            setContentView(R.layout.info_fragment);
            Bundle video = new Bundle();
            video.putString("sdesc", sd);
            video.putString("desc", d);
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
}

