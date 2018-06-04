package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.session.PlaybackState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.EventListener;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailFragment extends Fragment{

    static SimpleExoPlayer player;
    static Context _context;
    static boolean landscape = false;
    boolean pushed = false;


    static Button next;

    Intent intent;

    SimpleExoPlayerView simpleExoPlayerView;
    static boolean playbackState = false;
    static long playbackPos = 0;


    static View mView;
    ArrayList<String> videoList;
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
            videoList = getArguments().getStringArrayList("videos");

        }

        if (savedInstanceState!=null){
            playbackState = savedInstanceState.getBoolean("videoState");
            playbackPos = savedInstanceState.getLong("videoPosition");
        }else{
            playbackState = false;
            playbackPos = 0;
        }

        View view = inflater.inflate(R.layout.detail_recipe_fragment, container, false);
        mView = view;


        if (getString(R.string.size).equals("small") && !landscape){

            TextView tv = (TextView) view.findViewById(R.id.stei);
            tv.setText(stepText);
            TextView tvd = (TextView) view.findViewById(R.id.sted);
            tvd.setText(stepTextD);

            next = (Button) view.findViewById(R.id.nextButton);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    pushed = true;
                    Intent nextIntent = new Intent(getContext(), AuxActivity.class);
                    Bundle bundle = new Bundle();
                           stopPlayer();
                            playbackPos = 0;
                            playbackState = false;

                            bundle.putString("video", videoList.get(step+1));
                            bundle.putInt("step", step+1);
                            bundle.putStringArrayList("videos", videoList);
                            nextIntent.putParcelableArrayListExtra("stepO", intent.getParcelableArrayListExtra("stepO"));
                            nextIntent.putExtra("data", bundle);

                    getActivity().finish();
                    startActivity(nextIntent);
                }
            });

            checkNextStep(next);

        }

        createVideoPlayer(view);

        loadVideo();

        return view;

    }

    @Override
    public void onPause() {

        super.onPause();
        if (pushed)
            stopPlayer();


    }

    @Override
    public void onStop(){
        super.onStop();
        if (!pushed)
            stopPlayer();

    }

    @Override

    public void onResume() {
        super.onResume();
        if (player==null){
            createVideoPlayer(mView);
            loadVideo();
        }

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState!=null){
            playbackPos = savedInstanceState.getLong("videoPosition");
            playbackState = savedInstanceState.getBoolean("videoState");
        }

    }

    public static void stopPlayer(){

        if (player!=null) {
            playbackPos = player.getCurrentPosition();
            playbackState = player.getPlayWhenReady();

            player.setPlayWhenReady(false);
            player.release();
            player = null;

        }
    }

    public void checkNextStep(Button next){

        if (videoList.size()-1==step)

            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (player!=null){
            outState.putBoolean("videoState", player.getPlayWhenReady());
            outState.putLong("videoPosition", player.getCurrentPosition());

            stopPlayer();


        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);


    }

    private void createVideoPlayer(View view){
        simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.sepv);


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);


        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


        simpleExoPlayerView.setPlayer(player);

        DefaultBandwidthMeter dbandwidthMeter = new DefaultBandwidthMeter();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)), dbandwidthMeter);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        Uri uri = null;


        uri = Uri.parse(value);



        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);

        player.prepare(videoSource);
    }

    private void loadVideo(){
        player.seekTo(playbackPos);
        player.setPlayWhenReady(playbackState);
    }

    public void setVideo(String vid){
        value = vid;
    }


}

