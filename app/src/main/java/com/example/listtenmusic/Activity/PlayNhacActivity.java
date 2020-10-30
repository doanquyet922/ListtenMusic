package com.example.listtenmusic.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.FragmentTransitionSupport;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtenmusic.Adapter.ViewPagerPlayNhac;
import com.example.listtenmusic.Fragment.FragmentDanhSachCacBaihat;
import com.example.listtenmusic.Fragment.FragmentDiaNhac;
import com.example.listtenmusic.Fragment.FragmentMiniPlay;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.PlayNhacService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import static com.example.listtenmusic.R.*;
import static com.example.listtenmusic.R.drawable.pause_playnhac;

public class PlayNhacActivity extends AppCompatActivity {


    Toolbar toolbarplaynhac;
    public static TextView tTimesong, tTimetong;
    public static SeekBar seekBartime;
    public static ImageButton bPlay, bShuffle, bSkiptostart, bNext, bRepeat;
    ViewPager viewPagerPlaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlayNhac adapterPlaynhac;
    FragmentDiaNhac fragmentDiaNhac;
    FragmentDanhSachCacBaihat fragmentDanhSachCacBaihat;
    public static MediaPlayer mediaPlayer;
    public static int pos = 0;
    public static boolean repeat = false;
    public static boolean checkrandom = false;
    boolean next = false;
    public static String TenBaiHat, LinkHinhAnh;
    private boolean iboundservice = false;
    PlayNhacService playNhacService;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_play_nhac);
        //Tranhs phat sinh khi su dung mang, Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


//        Log.d("BBB", "onCreate: ");
//        if(iboundservice==true){
//            unbindService(serviceConnection);
//            iboundservice=false;
//        }
        GetDataFromIntent();
//        NhanDataSauClickMiniPlay();
        init();
        eventsClick();


    }

    private void ConnectService(@Nullable final ArrayList<BaiHat> arrbaihat, @Nullable final Integer position) {
//        playNhacService.setMediaPlayer("https://quyetdaik922.000webhostapp.com/MP3/Ai%20Mang%20Co%20Don%20Di%20-%20K-ICM_%20APJ.mp3");
        Intent intent = new Intent(PlayNhacActivity.this, PlayNhacService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayNhacService.BoundExample binder = (PlayNhacService.BoundExample) service;
                playNhacService = binder.getService();
                playNhacService.setMediaPlayer(arrbaihat.get(position).getLinkBaiHat());
                iboundservice = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iboundservice = false;
            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private void eventsClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterPlaynhac.getItem(1) != null) {
                    if (mangbaihat.size() > 0) {
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacService.mediaPlayer.isPlaying()) {
                    PlayNhacService.mediaPlayer.pause();
                    bPlay.setImageResource(drawable.play_playnhac);
                } else {
                    PlayNhacService.mediaPlayer.start();
                    bPlay.setImageResource(R.drawable.pause_playnhac);
                }
            }
        });
        bRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkrandom == true) {
                        checkrandom = false;
                        bRepeat.setImageResource(drawable.repeat_true_playnhac);
                        bShuffle.setImageResource(drawable.shuffle_playnhac);

                    }
                    bRepeat.setImageResource(drawable.repeat_true_playnhac);
                    repeat = true;
                } else {
                    bRepeat.setImageResource(drawable.repeat_playnhac);
                    repeat = false;
                }
            }
        });
        bShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkrandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        bShuffle.setImageResource(drawable.shuffle_true_playnhac);
                        bRepeat.setImageResource(drawable.repeat_playnhac);

                    }
                    bShuffle.setImageResource(drawable.shuffle_true_playnhac);
                    checkrandom = true;
                } else {
                    bShuffle.setImageResource(drawable.shuffle_playnhac);
                    checkrandom = false;
                }
            }
        });
        seekBartime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PlayNhacService.mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                            PlayNhacService.mediaPlayer.stop();
//                            PlayNhacService.mediaPlayer.release();
//                            PlayNhacService.mediaPlayer = null;
//                    }
                    if (pos < (mangbaihat.size())) {
                        bPlay.setImageResource(pause_playnhac);
                        pos++;
                        if (repeat == true) {
                            if (pos == 0) {
                                pos = mangbaihat.size();
                            }
                            pos -= 1;

                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == pos) {
                                pos = index - 1;
                            }
                            pos = index;
                        }
                        if (pos > (mangbaihat.size() - 1)) {
                            pos = 0;

                        }
//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(mangbaihat, pos);
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
                        UpdateTime();
                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
                    }
                }
                bSkiptostart.setClickable(false);
                bNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bSkiptostart.setClickable(true);
                        bNext.setClickable(true);
                    }
                }, 5000);

            }

        });
        bSkiptostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                        PlayNhacService.mediaPlayer.stop();
//                        PlayNhacService.mediaPlayer.release();
//                        PlayNhacService.mediaPlayer = null;
//                    }
                    if (pos < (mangbaihat.size())) {
                        bPlay.setImageResource(pause_playnhac);
                        pos--;
                        if (pos < 0) {
                            pos = mangbaihat.size() - 1;
                        }
                        if (repeat == true) {

                            pos += 1;

                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == pos) {
                                pos = index - 1;
                            }
                            pos = index;
                        }

//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(mangbaihat, pos);
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
                        UpdateTime();
                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
                    }
                }
                bSkiptostart.setClickable(false);
                bNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bSkiptostart.setClickable(true);
                        bNext.setClickable(true);
                    }
                }, 5000);

            }

        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")) {
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHatArrayList;
            }

        }

    }

    private void init() {
        toolbarplaynhac = findViewById(id.toolbarplaynhac);
        tTimesong = findViewById(id.tTimeSong);
        tTimetong = findViewById(id.tTongThoiGian);
        seekBartime = findViewById(id.seebarSong);
        bShuffle = findViewById(id.imshuffle);
        bSkiptostart = findViewById(id.imSkiptostart);
        bPlay = findViewById(id.implay);
        bNext = findViewById(id.imNext);
        bRepeat = findViewById(id.imRepeat);
        viewPagerPlaynhac = findViewById(id.viewpagerPlaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragmentDiaNhac = new FragmentDiaNhac();
        fragmentDanhSachCacBaihat = new FragmentDanhSachCacBaihat();
        adapterPlaynhac = new ViewPagerPlayNhac(getSupportFragmentManager());
        adapterPlaynhac.AddFragment(fragmentDanhSachCacBaihat);
        adapterPlaynhac.AddFragment(fragmentDiaNhac);
        viewPagerPlaynhac.setAdapter(adapterPlaynhac);
        fragmentDiaNhac = (FragmentDiaNhac) adapterPlaynhac.getItem(1);
        if (mangbaihat.size() > 0) {
//            if (mediaPlayer != null) {
//                mediaPlayer.stop();
//                mediaPlayer.reset();
//            }
                getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
//                new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                ConnectService(mangbaihat, 0);
                bPlay.setImageResource(R.drawable.pause_playnhac);
                TenBaiHat = mangbaihat.get(0).getTenBaiHat();
                LinkHinhAnh = mangbaihat.get(0).getHinhBaiHat();
            }
        }


//    class PlayMP3 extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String baihat) {
//            super.onPostExecute(baihat);
//            try {
//                if (mediaPlayer == null) {
//                    mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            mediaPlayer.stop();
//                            mediaPlayer.reset();
//                        }
//                    });
//                }
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }
//                if (!baihat.equals("")) {
//                    mediaPlayer=new MediaPlayer();
//                    mediaPlayer.setDataSource(baihat);
//                    mediaPlayer.prepare();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.start();
////            Log.d("CCC", "init: "+mediaPlayer.getDuration());
//            TimeSong();
//            UpdateTime();
//        }
//    }

    //    private void TimeSong() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        tTimetong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getDuration()));
//        seekBartime.setMax(PlayNhacService.mediaPlayer.getDuration());
//    }
    public void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
//                    Log.d("BBB", "run: "+PlayNhacService.mediaPlayer.getCurrentPosition());
                    seekBartime.setProgress(PlayNhacService.mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tTimesong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getCurrentPosition()));
//                    if (PlayNhacService.isNext == true) {
//                        next = true;
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    handler.postDelayed(this, 250);
                }
            }
        }, 250);
//        final Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (next == true) {
//                    if (pos < (mangbaihat.size())) {
//                        bPlay.setImageResource(pause_playnhac);
//                        pos++;
//                        if (repeat == true) {
//                            if (pos == 0) {
//                                pos = mangbaihat.size();
//                            }
//                            pos -= 1;
//
//                        }
//                        if (checkrandom == true) {
//                            Random random = new Random();
//                            int index = random.nextInt(mangbaihat.size());
//                            if (index == pos) {
//                                pos = index - 1;
//                            }
//                            pos = index;
//                        }
//                        if (pos > (mangbaihat.size() - 1)) {
//                            pos = 0;
//
//                        }
////                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
//                        ConnectService(mangbaihat, pos);
////
////                        Log.d("CCC", "run: " + mangbaihat.get(pos).getLinkBaiHat());
////                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
////                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
//                    }
//
//                    bSkiptostart.setClickable(false);
//                    bNext.setClickable(false);
//                    Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            bSkiptostart.setClickable(true);
//                            bNext.setClickable(true);
//                        }
//                    }, 5000);
//                    next = false;
//                    handler1.removeCallbacks(this);
//                } else {
//                    handler1.postDelayed(this, 1000);
//                }
//            }
//        }, 1000);


    }

    public void NhanDataSauClickMiniPlay() {
        Intent intent = getIntent();
        if (intent.hasExtra("miniplay")) {
            LayDulieutuPlayNhac layDulieutuPlayNhac = (LayDulieutuPlayNhac) intent.getParcelableExtra("miniplay");
//            Log.d("BBB", "GetDataFromIntent: "+layDulieutuPlayNhac.getMangbaihat().get(layDulieutuPlayNhac.getPos()).getTenBaiHat());
////           mangbaihat.clear();
            mangbaihat = layDulieutuPlayNhac.getMangbaihat();
            pos = layDulieutuPlayNhac.getPos();
            repeat = layDulieutuPlayNhac.isRepeat();
            checkrandom = layDulieutuPlayNhac.isCheckrandom();
//            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
//            mediaPlayer=Layout_main.mediaPlayerLU;
//            seekBartime.setProgress(Layout_main.du);
            Log.d("BBB", "NhanDataSauClickMiniPlay: " + Layout_main.mediaPlayerLU.getCurrentPosition());
        }
    }
}