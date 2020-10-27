package com.example.listtenmusic.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.listtenmusic.R.drawable.pause_playnhac;


public class FragmentMiniPlay  extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.miniplay,container,false);
        final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar) ;
        ImageButton bNext=(ImageButton) view.findViewById(R.id.bEnd);
        ImageButton bSkiptostart=(ImageButton) view.findViewById(R.id.bStart);
        final CircleImageView imChay=(CircleImageView) view.findViewById(R.id.imChay);
        final TextView tTenBaiHat=(TextView)view.findViewById(R.id.textViewMini);
        final ImageButton bPlay=(ImageButton) view.findViewById(R.id.bPlayMini);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacActivity.mediaPlayer.isPlaying()) {
                    PlayNhacActivity.mediaPlayer.pause();
                    bPlay.setImageResource(R.drawable.play1);
                } else {
                    PlayNhacActivity.mediaPlayer.start();
                    bPlay.setImageResource(R.drawable.pause1);
                }
            }
        });
        bSkiptostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayNhacActivity.bSkiptostart.callOnClick();

            }
        });
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayNhacActivity.bNext.callOnClick();

            }
        });
        final String[] t = {""};
        final Animation animation=AnimationUtils.loadAnimation(getActivity(),R.anim.anim_rotate);
        imChay.startAnimation(animation);
        final int[] k = {0};
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacActivity.mediaPlayer != null) {
                    if (PlayNhacActivity.mediaPlayer.isPlaying()) {
                        t[0] =PlayNhacActivity.TenBaiHat+"";
                        tTenBaiHat.setText(t[0]);
                        Picasso.with(getActivity()).load(PlayNhacActivity.LinkHinhAnh).into(imChay);
                        k[0] =PlayNhacActivity.mediaPlayer.getDuration();
                        bPlay.setImageResource(R.drawable.pause1);
                    }
                    else {
                        bPlay.setImageResource(R.drawable.play1);

                    }
                }
                handler.postDelayed(this,1000);
            }
        }, 1000);
        final Handler handlerProgress=new Handler();
        handlerProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                int d=0;
                if(d!=k[0]){
                    d=k[0];
                    progressBar.setMax(d);
                    if(PlayNhacActivity.mediaPlayer!=null) {
                        progressBar.setProgress(PlayNhacActivity.mediaPlayer.getCurrentPosition());
                    }
                }
                handlerProgress.postDelayed(this,500);
            }
        },500);
        return view;
    }
}
