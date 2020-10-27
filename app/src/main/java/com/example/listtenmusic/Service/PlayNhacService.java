package com.example.listtenmusic.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.listtenmusic.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayNhacService extends Service {
    private WindowManager mWindowManager;
    private ViewGroup viewGroup;
    private WindowManager.LayoutParams mParams;
    private View playingview ;
    View miniplay,playnhac;
    CircleImageView imChay;
    TextView textViewMini;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        playingview=inflater.inflate(R.layout.phaynhac_service,null,false);
//        //Add the view to the window.
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        //Add the view to the window
//        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        mWindowManager.addView(playingview, params);

//        mWindowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
        viewGroup=new ViewGroup(this) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };
        LayoutInflater inflater=LayoutInflater.from(this);
        playingview=inflater.inflate(R.layout.phaynhac_service,viewGroup);
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.CENTER;
        mParams.format = PixelFormat.TRANSLUCENT;//trong suot
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;// noi tren all be mat
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// khong bi gioi han boi man hinh|Su duoc nut home
        mWindowManager.addView(playingview,mParams);

//        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
//        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view=layoutInflater.inflate(R.layout.phaynhac_service, viewGroup);
//        mParams=new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_APPLICATION,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        mParams.gravity=Gravity.CENTER|Gravity.CENTER;
//        mParams.x=0;
//        mParams.y=0;
//        mWindowManager.addView(viewGroup, mParams);

        miniplay=playingview.findViewById(R.id.miniplayLayout);
        playnhac=playingview.findViewById(R.id.playnhacLayout);
    }
}
