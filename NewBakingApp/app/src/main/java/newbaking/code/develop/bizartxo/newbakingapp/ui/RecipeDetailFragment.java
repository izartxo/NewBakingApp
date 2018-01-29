package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailFragment extends Fragment {

    static SimpleExoPlayer player;
    static Context _context;
    static boolean landscape = false;

    static Button next;

    Intent intent;

    SimpleExoPlayerView simpleExoPlayerView;

    ArrayList<String> sl;
    String value="";
    int step=0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getActivity().getIntent();

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            landscape = true;
        }
        else {
            // Portrait
            landscape = false;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String stepText = "";
        String stepTextD = "";

        if ( getArguments()!=null ) {
            step = getArguments().getInt("step");
            value = getArguments().getString("video");
            stepText = getArguments().getString("sdesc");
            stepTextD = getArguments().getString("desc");
            sl = getArguments().getStringArrayList("videos");
            Log.d("vaaaaaaaaaaaa","luuuuuuuuuuuue: " + value);
        }

        View view = inflater.inflate(R.layout.detail_recipe_fragment, container, false);



        if (getString(R.string.size).equals("small") && !landscape){

            TextView tv = (TextView) view.findViewById(R.id.stei);
            tv.setText(stepText);
            TextView tvd = (TextView) view.findViewById(R.id.sted);
            tvd.setText(stepTextD);

            /*for (int i = 0; i < container.getChildCount(); i++){
                Log.d("CHIIIIIIIIIIIIIIIIIILD", "View: " + container.getChildAt(i).getClass().getName() + " Pos: " + i);
                try{
                    ViewGroup vg = (ViewGroup) container.getChildAt(i).getRootView();
                    for (int j = 0; j < vg.getChildCount(); j++){
                        Log.d("CHIIIIIIIIIIIIIIIIIILD", "View: " + container.getChildAt(j).getClass().getName() + " Pos: " + j);
                        ViewGroup vg2 = (ViewGroup) container.getChildAt(j).getRootView();
                        for (int m = 0; m < vg2.getChildCount(); m++){
                            Log.d("CHIIIIIIIIIIIIIIIIIILD", "View: " + container.getChildAt(m).getClass().getName() + " Pos: " + m);
                            ViewGroup vg3 = (ViewGroup) container.getChildAt(m).getRootView();
                            for (int n = 0; n < vg3.getChildCount(); n++){
                                Log.d("CHIIIIIIIIIIIIIIIIIILD", "View: " + container.getChildAt(n).getClass().getName() + " Pos: " + n);

                            }
                        }
                    }
                }catch(Exception e){

                }

            }*/

            next = (Button) view.findViewById(R.id.nextButton);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent nextIntent = new Intent(getContext(), AuxActivity.class);
                    Bundle bundle = new Bundle();


                            bundle.putString("video", sl.get(step+1));
                            bundle.putInt("step", step+1);
                            bundle.putStringArrayList("videos",sl);
                            nextIntent.putParcelableArrayListExtra("stepO", intent.getParcelableArrayListExtra("stepO"));
                            nextIntent.putExtra("data", bundle);

                    getActivity().finish();
                    startActivity(nextIntent);
                }
            });

            checkNextStep(next);


        }

        simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.sepv);
        // 1. Create a default TrackSelector

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);



// 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        
        simpleExoPlayerView.setPlayer(player);



        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter dbandwidthMeter = new DefaultBandwidthMeter();
// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "yourApplicationName"), dbandwidthMeter);
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        Uri uri = Uri.parse(value); //"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.
        player.prepare(videoSource);

        return view;

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override

    public void onResume() {
        super.onResume();

    }

    public static void stopPlayer(){


        if (player!=null) {
            Toast.makeText(_context, "Releasing video...", Toast.LENGTH_SHORT).show();
            player.setPlayWhenReady(false);
            player.release();
            player = null;

        }
    }

    public void checkNextStep(Button next){

        if (sl.size()-1==step)

            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);
    }

}

