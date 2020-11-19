package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideoActivity extends AppCompatActivity {
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView bfullscreen,b_backvideo,bLoveVideo;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag=false;
    TextView tTenVideo;
BaiHat baiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        init();
        GetDataIntent();
        events();
    }

    private void events() {
        Uri uri=Uri.parse(baiHat.getLinkBaiHat());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LoadControl loadControl=new DefaultLoadControl();

        BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();

        TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(PlayVideoActivity.this,trackSelector,loadControl);
//
//        String userAgent = Util.getUserAgent(MainActivity.this,getApplicationName(MainActivity.this));
//        DefaultHttpDataSourceFactory factory=new DefaultHttpDataSourceFactory(userAgent,
//                null /* listener */,
//                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
//                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
//                true /* allowCrossProtocolRedirects */);
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, Util.getUserAgent(MainActivity.this, getApplicationName(MainActivity.this)));
        DefaultHttpDataSourceFactory factory=new DefaultHttpDataSourceFactory(getApplicationName(PlayVideoActivity.this));
        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();



        MediaSource mediaSource=new ExtractorMediaSource(uri,factory,extractorsFactory,null,null);

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);

        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //check condition
                if(playbackState==Player.STATE_BUFFERING){
                    //when buffering
                    //shoew progress bar
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState==Player.STATE_READY){
                    //When readey
                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        bfullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==true){
                    bfullscreen.setImageResource(R.drawable.ic_fullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag=false;
                }
                else {
                    bfullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag=true;
                }
            }
        });
        b_backvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tTenVideo.setText(baiHat.getTenBaiHat());
        bLoveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLoveVideo.setImageResource(R.drawable.icon_love_true);
                Dataservice dataservice= APIService.getService();
                Call<String> callback=dataservice.UpdateLuotThich("1",baiHat.getIDBaiHat());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String kq=response.body();
                        if(kq.equals("ok")){
                            Toast.makeText(PlayVideoActivity.this,"Đã thích",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PlayVideoActivity.this,"Bị lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                bLoveVideo.setEnabled(false);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        //stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        //get playback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //play video when redy
        simpleExoPlayer.setPlayWhenReady(true);

        //get playback state
        simpleExoPlayer.getPlaybackState();

    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }


    private void GetDataIntent() {
        Intent intent=getIntent();
        if (intent != null) {
            if (intent.hasExtra("videos")) {
                baiHat = intent.getParcelableExtra("videos");

            }
        }

    }

    private void init() {

        playerView=(PlayerView) findViewById(R.id.player_view);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        bfullscreen=(ImageView) findViewById(R.id.fullscreen);
        b_backvideo=(ImageView) findViewById(R.id.im_backvideo);
        bLoveVideo=(ImageView) findViewById(R.id.imLoveVideo);
        tTenVideo=(TextView) findViewById(R.id.tTenVideo);

    }

}