package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.FragmentTransitionSupport;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import static com.example.listtenmusic.R.*;
import static com.example.listtenmusic.R.drawable.pause_playnhac;

public  class PlayNhacActivity2 extends AppCompatActivity {


    Toolbar toolbarplaynhac;
    TextView tTimesong, tTimetong;
    SeekBar seekBartime;
    public static ImageButton bPlay, bShuffle, bSkiptostart, bNext, bRepeat;
    ViewPager viewPagerPlaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlayNhac adapterPlaynhac;
    FragmentDiaNhac fragmentDiaNhac;
    FragmentDanhSachCacBaihat fragmentDanhSachCacBaihat;
    MediaPlayer mediaPlayer;
    int pos = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;
    public static String TenBaiHat, LinkHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_play_nhac);
        //Tranhs phat sinh khi su dung mang, Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NhanDataSauClickMiniPlay();
        init();
        eventsClick();

//        Log.d("BBB", "onCreate: ");
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
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    bPlay.setImageResource(drawable.play_playnhac);
//                } else {
//                    mediaPlayer.start();
//                    bPlay.setImageResource(R.drawable.pause_playnhac);
//                }
                PlayNhacActivity.bPlay.callOnClick();
            }
        });
        bRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (repeat == false) {
//                    if (checkrandom == true) {
//                        checkrandom = false;
//                        bRepeat.setImageResource(drawable.repeat_true_playnhac);
//                        bShuffle.setImageResource(drawable.shuffle_playnhac);
//
//                    }
//                    bRepeat.setImageResource(drawable.repeat_true_playnhac);
//                    repeat = true;
//                } else {
//                    bRepeat.setImageResource(drawable.repeat_playnhac);
//                    repeat = false;
//                }

            }
        });
        bShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (checkrandom == false) {
//                    if (repeat == true) {
//                        repeat = false;
//                        bShuffle.setImageResource(drawable.shuffle_true_playnhac);
//                        bRepeat.setImageResource(drawable.repeat_playnhac);
//
//                    }
//                    bShuffle.setImageResource(drawable.shuffle_true_playnhac);
//                    checkrandom = true;
//                } else {
//                    bShuffle.setImageResource(drawable.shuffle_playnhac);
//                    checkrandom = false;
//                }

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
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mangbaihat.size() > 0) {
//                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                    }
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
//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
//                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
//                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
//                    }
//                }
//                bSkiptostart.setClickable(false);
//                bNext.setClickable(false);
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        bSkiptostart.setClickable(true);
//                        bNext.setClickable(true);
//                    }
//                }, 5000);
                PlayNhacActivity.bNext.callOnClick();
            }

        });
        bSkiptostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mangbaihat.size() > 0) {
//                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                    }
//                    if (pos < (mangbaihat.size())) {
//                        bPlay.setImageResource(pause_playnhac);
//                        pos--;
//                        if (pos < 0) {
//                            pos = mangbaihat.size() - 1;
//                        }
//                        if (repeat == true) {
//
//                            pos += 1;
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
//
//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
//                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
//                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
//                    }
//                }
//                bSkiptostart.setClickable(false);
//                bNext.setClickable(false);
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        bSkiptostart.setClickable(true);
//                        bNext.setClickable(true);
//                    }
//                }, 5000);
                PlayNhacActivity.bSkiptostart.callOnClick();

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
//            mediaPlayer=new MediaPlayer();
            getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//            new PlayMP3().execute(mangbaihat.get(0).getLinkBaiHat());
            bPlay.setImageResource(R.drawable.pause_playnhac);
            TenBaiHat = mangbaihat.get(0).getTenBaiHat();
            LinkHinhAnh = mangbaihat.get(0).getHinhBaiHat();

        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tTimetong.setText(simpleDateFormat.format(PlayNhacActivity.mediaPlayer.getDuration()));
        seekBartime.setMax(PlayNhacActivity.mediaPlayer.getDuration());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    seekBartime.setProgress(PlayNhacActivity.mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tTimesong.setText(simpleDateFormat.format(PlayNhacActivity.mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    PlayNhacActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            }
        }, 300);
//        if (repeat==true){
//            bRepeat.setImageResource(drawable.repeat_true_playnhac);
//        }
//        if (checkrandom==true){
//            bShuffle.setImageResource(drawable.shuffle_true_playnhac);
//        }
    }

    class PlayMP3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                if (!baihat.equals("")) {
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(baihat);
                    mediaPlayer.prepare();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
//            Log.d("CCC", "init: "+mediaPlayer.getDuration());
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tTimetong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBartime.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBartime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next==true){
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
                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
                        UpdateTime();
                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
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
                    next=false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        }, 1000);

    }
    public void NhanDataSauClickMiniPlay(){
        Intent intent=getIntent();
        if (intent.hasExtra("miniplay")) {
            LayDulieutuPlayNhac layDulieutuPlayNhac= (LayDulieutuPlayNhac) intent.getParcelableExtra("miniplay");
//            Log.d("BBB", "GetDataFromIntent: "+layDulieutuPlayNhac.getMangbaihat().get(layDulieutuPlayNhac.getPos()).getTenBaiHat());
////           mangbaihat.clear();
            mangbaihat=layDulieutuPlayNhac.getMangbaihat();
            pos=layDulieutuPlayNhac.getPos();
            repeat=layDulieutuPlayNhac.isRepeat();
            checkrandom=layDulieutuPlayNhac.isCheckrandom();

//            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());

//            mediaPlayer=Layout_main.mediaPlayerLU;
//            seekBartime.setProgress(Layout_main.du);
//            Log.d("BBB", "NhanDataSauClickMiniPlay: "+Layout_main.mediaPlayerLU.getCurrentPosition());
        }
    }
}