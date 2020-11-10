package com.example.listtenmusic.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.listtenmusic.Adapter.ConnectionReceiver;
import com.example.listtenmusic.Adapter.MainViewPagerAdapter;
import com.example.listtenmusic.Adapter.QuangCaoAdapter;
import com.example.listtenmusic.Fragment.FragmengtOnline;
import com.example.listtenmusic.Fragment.FragmentMenu;
import com.example.listtenmusic.Fragment.FragmentMiniPlay;
import com.example.listtenmusic.Fragment.FragmentOffline;
import com.example.listtenmusic.Fragment.FragmentQuangCao;
import com.example.listtenmusic.Fragment.FragmentTimKiem;
import com.example.listtenmusic.Fragment.Fragment_trangchu;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.PlayNhacService;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Layout_main extends AppCompatActivity {
    Button bOffline, bOnline;
    ImageButton bMenu;
    DrawerLayout drawerLayout;
    RelativeLayout reMini;
    TabLayout tabLayout;
    ViewPager viewPager;
    Button bHDSD,bTTUD,bDSYT,bHenGio;
public static int du;
    FragmengtOnline fragmengtOnline = new FragmengtOnline();
    FragmentOffline fragmentOffline = new FragmentOffline();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        checkAndRequestPermissions();
        init();
        events();
        boolean ret = ConnectionReceiver.isConnected();
        String msg = null;
        if (ret == true) {
            tabLayout.getTabAt(1).select();
            msg = "Chúc bạn nghe nhạc vui vẻ";
        } else {
            tabLayout.getTabAt(0).select();
            msg = "Thiết bị chưa kết nối internet";
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_main.this,PlayNhacActivity.class);
                ArrayList<BaiHat> mangbaihat=PlayNhacActivity.mangbaihat;
                int pos=PlayNhacActivity.pos;
                boolean repeat=PlayNhacActivity.repeat;
                boolean checkrandom=PlayNhacActivity.checkrandom;

                LayDulieutuPlayNhac layDulieutuPlayNhac=new LayDulieutuPlayNhac(mangbaihat,pos,repeat,checkrandom);
                intent.putExtra("miniplay",layDulieutuPlayNhac);
                startActivity(intent);

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
                    if (PlayNhacService.mediaPlayer.isPlaying()){
                        reMini.setVisibility(View.VISIBLE);}
                }
                handler.postDelayed(this,300);
            }
        }, 300);
    }

    private void events() {
        bHDSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Layout_main.this,HuongDanSuDungActivity.class);
                startActivity(intent);
            }
        });
        bTTUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Layout_main.this,ThongTinUngDungActivity.class);
                startActivity(intent);
            }
        });
    }
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    private void init() {
        bHDSD=(Button) findViewById(R.id.bHuongdan);
        bTTUD=(Button) findViewById(R.id.bThongtin);
        bDSYT=(Button) findViewById(R.id.bDanhsachYeuThich);
        bHenGio=(Button) findViewById(R.id.bHengio);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        bOnline = (Button) findViewById(R.id.bOnline);
        bOffline = (Button) findViewById(R.id.bOffline);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        reMini = (RelativeLayout) findViewById(R.id.reMini);
        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutmain);
        viewPager = (ViewPager) findViewById(R.id.viewpagerLayoutmain);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentOffline(), "Offline");
        mainViewPagerAdapter.addFragment(new FragmengtOnline(), "Online");
        mainViewPagerAdapter.addFragment(new FragmentTimKiem(),"");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(2).setIcon(R.drawable.timkiem);

    }

    //    public void AddFragment(View view){
//        FragmentManager fragmentManager1=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
//        switch (view.getId()){
//            case R.id.bOffline:
//                fragmentTransaction1.replace(R.id.fragContent,fragmengtOnline);
//                fragmentTransaction1.addToBackStack(null);
//                fragmentTransaction.commit();
//                break;
//            case R.id.bOnline:
//                fragmentTransaction1.replace(R.id.fragContent,fragmentOffline);
//                fragmentTransaction1.addToBackStack(null);
//                fragmentTransaction.commit();
//                break;
//        }
//    }
//    private void GetDataOnline() {
//        Dataservice dataservice = APIService.getService();
//
//        Call<List<QuangCao>> callback = dataservice.GetDataQuangCao();
//        callback.enqueue(new Callback<List<QuangCao>>() {
//            @Override
//            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
//                ArrayList<QuangCao> quangCaos = (ArrayList<QuangCao>) response.body();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<QuangCao>> call, Throwable t) {
//                Log.d("ccc", " loi:" + t);
//            }
//        });
//    }


}
