package udacity.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import udacity.bakingapp.R;
import udacity.bakingapp.model.Recipe;
import udacity.bakingapp.model.Steps;

public class FragmentDetails extends Fragment {

    private TextView stepText;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private Boolean urlExists;
    private int position;

    private Recipe recipe;
    private Steps step;

    private long playerPosition;
    private boolean state = true;

    //mandatory empty constructor
    public FragmentDetails(){
    }

    public void updatePosition(int position) {
        this.position = position;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe", recipe);

            outState.putLong("PLAYER_POSITION", playerPosition);
            outState.putBoolean("STATE", state);
            Log.e("position stored: ", playerPosition + "");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        stepText = view.findViewById(R.id.step_text);

        restoreStates(savedInstanceState);

        if(getResources().getBoolean(R.bool.isTablet)){
            if(getActivity().getIntent().getParcelableExtra("recipe") != null){
                recipe = getActivity().getIntent().getParcelableExtra("recipe");
            }

            if(recipe == null){
                if(savedInstanceState != null){
                    recipe = savedInstanceState.getParcelable("recipe");
                }
            }

            if(savedInstanceState != null){
                position = savedInstanceState.getInt("position");
                //Log.e("position", savedInstanceState.getInt("position") + "");
            }

            step = recipe.getSteps().get(position);
        }else {
            step = getActivity().getIntent().getParcelableExtra("step");
        }


        stepText.setText(step.getDescription());

        urlExists = !step.getVideoURL().equals("");

        Log.e("from the bottom: ", playerPosition + "");
        //player
        mPlayerView = view.findViewById(R.id.exo_player);

        /*
        if(urlExists){
            initializePlayer(Uri.parse(step.getVideoURL()));
        }else mPlayerView.setVisibility(View.GONE);
        */
        return  view;
    }

    private void restoreStates(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey("PLAYER_POSITION")){
            playerPosition = savedInstanceState.getLong("PLAYER_POSITION");
            state = savedInstanceState.getBoolean("STATE");
            Log.e("position restored: ", playerPosition + "");
        }
    }

    private void initializePlayer(Uri uri){
        if(mExoPlayer == null){
            //Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            //Prepare the media source
            String userAgent = Util.getUserAgent(getActivity(), "classicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            Log.e("Initialize", "running");

            mExoPlayer.prepare(mediaSource);

                    mExoPlayer.seekTo(playerPosition);
                    mExoPlayer.setPlayWhenReady(state);
                    Log.e("position to: ", playerPosition + "");

        }
    }


    private void pausePlayer(){
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.getPlaybackState();
        }

    }
    private void startPlayer(){
        if(mExoPlayer != null) {
            if(playerPosition != 0){
                mExoPlayer.seekTo(playerPosition);
                mExoPlayer.setPlayWhenReady(state);
            }else{
                mExoPlayer.setPlayWhenReady(true);
                mExoPlayer.getPlaybackState();
            }

        }
    }

    private void releasePlayer(){
        if(mExoPlayer!= null && urlExists){
            state = mExoPlayer.getPlayWhenReady();
            playerPosition = mExoPlayer.getCurrentPosition();
            Log.e("release player: ", playerPosition + "");

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(urlExists){
            initializePlayer(Uri.parse(step.getVideoURL()));
        }else mPlayerView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause position: ", playerPosition + "");
        releasePlayer();
    }



}
