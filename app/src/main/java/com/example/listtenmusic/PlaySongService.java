package com.example.listtenmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.MediaController;

public class PlaySongService extends Service {
    MediaPlayer mediaPlayer;
    public PlaySongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        MediaController mediaController=MediaController.();

        return super.onStartCommand(intent, flags, startId);
    }

}
