package com.example.listtenmusic.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.listtenmusic.R.drawable.pause_playnhac;

public class PlayNhacService extends Service {
    IBinder iBinder = new BoundExample();
    ArrayList<BaiHat> arr;
    boolean next;
    public  static MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class BoundExample extends Binder {
        public PlayNhacService getService() {
            return PlayNhacService.this;
        }
    }

//    public String getCurrentTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:mm:ss MM/dd/yyyy");
//        return (dateFormat.format(new Date()));
//    }
    public void setMediaPlayer(String link){
        new PlayMP3().execute(link);
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
            PlayNhacActivity playNhacActivity=new PlayNhacActivity();
            playNhacActivity.UpdateTime();
        }
    }
    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        PlayNhacActivity.tTimetong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        PlayNhacActivity.seekBartime.setMax(mediaPlayer.getDuration());
    }
//    public  void UpdateTime() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (PlayNhacService.mediaPlayer != null) {
//                    PlayNhacActivity.seekBartime.setProgress(PlayNhacService.mediaPlayer.getCurrentPosition());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                    PlayNhacActivity.tTimesong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getCurrentPosition()));
//                    handler.postDelayed(this, 300);
//                    PlayNhacService.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            next = true;
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        }, 300);
//        final Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(next==true){
//                    if (PlayNhacActivity.pos < (PlayNhacActivity.mangbaihat.size())) {
//                        PlayNhacActivity.bPlay.setImageResource(pause_playnhac);
//                        PlayNhacActivity.pos++;
//                        if (PlayNhacActivity.repeat == true) {
//                            if (PlayNhacActivity.pos == 0) {
//                                PlayNhacActivity.pos = PlayNhacActivity.mangbaihat.size();
//                            }
//                            PlayNhacActivity.pos -= 1;
//
//                        }
//                        if (PlayNhacActivity.checkrandom == true) {
//                            Random random = new Random();
//                            int index = random.nextInt(PlayNhacActivity.mangbaihat.size());
//                            if (index == PlayNhacActivity.pos) {
//                                PlayNhacActivity.pos = index - 1;
//                            }
//                            PlayNhacActivity.pos = index;
//                        }
//                        if (PlayNhacActivity.pos > (PlayNhacActivity.mangbaihat.size() - 1)) {
//                            PlayNhacActivity.pos = 0;
//
//                        }
////                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
//                        ConnectService(mangbaihat,pos);
//                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
//                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//                        PlayNhacActivity.TenBaiHat = PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getTenBaiHat();
//                        PlayNhacActivity.LinkHinhAnh = PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getHinhBaiHat();
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
//                    next=false;
//                    handler1.removeCallbacks(this);
//                }else {
//                    handler1.postDelayed(this,1000);
//                }
//            }
//        }, 1000);
//
//    }


}
