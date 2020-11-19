package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.listtenmusic.Adapter.MainViewPagerAdapter;
import com.example.listtenmusic.Fragment.FragmentBaiHatYeuThich;
import com.example.listtenmusic.Fragment.FragmentVideoYeuthich;
import com.example.listtenmusic.R;
import com.google.android.material.tabs.TabLayout;

public class DanhsachyeuthichActivity extends AppCompatActivity {
Toolbar toolbar;
ViewPager viewPager;
TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachyeuthich);
        toolbar=(Toolbar) findViewById(R.id.toolbarDanhsachyeuthich);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách yêu thích");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager=(ViewPager) findViewById(R.id.viewpagerDanhsachyethich);
        tabLayout=(TabLayout) findViewById(R.id.myTabLayoutYeuthich);

        MainViewPagerAdapter mainViewPagerAdapter=new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentBaiHatYeuThich(),"Bài hát");
        mainViewPagerAdapter.addFragment(new FragmentVideoYeuthich(),"Videos");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}