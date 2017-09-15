package newbaking.code.develop.bizartxo.newbakingapp.ui;

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
import android.widget.Button;
import android.widget.TextView;

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

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeDetailFragment extends Fragment {


    Intent intent;
    //TextView tv;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer player;
    //Button back;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getActivity().getIntent();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        String value = "";
        String stepText = "";
        String stepTextD = "";
        if (!(getArguments()==null)) {

            value = getArguments().getString("video");
            stepText = getArguments().getString("sdesc");
            stepTextD = getArguments().getString("desc");
            Log.d("vaaaaaaaaaaaa","luuuuuuuuuuuue: " + value);
        }



        View view = inflater.inflate(R.layout.detail_recipe_fragment, container, false);



        if (view.findViewById(R.id.land) == null){
            TextView tv = (TextView) view.findViewById(R.id.stei);
            tv.setText(stepText);
            TextView tvd = (TextView) view.findViewById(R.id.sted);
            tvd.setText(stepTextD);
            Button back = (Button) view.findViewById(R.id.backButton);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

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
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

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
        if (player!=null)
            player.release();
    }

    @Override

    public void onResume() {
        super.onResume();

        /*tv = (TextView) getActivity().findViewById(R.id.textView);
        tv.setText(intent.getExtras().getString("RID"));*/

    }

}
