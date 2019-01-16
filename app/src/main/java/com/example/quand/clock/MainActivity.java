package com.example.quand.clock;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        FragmentBaoThuc fragmentBaoThuc = (FragmentBaoThuc) adapter.getFragmentByPosition(FragmentBaoThuc.TAB_POSITION);
        fragmentBaoThuc.refreshAdapter();
        fragmentBaoThuc.updateAlarm();
    }

    private void addControl() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new PagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.alarm);
        tabLayout.getTabAt(1).setIcon(R.drawable.clock);
        tabLayout.getTabAt(2).setIcon(R.drawable.donghocat);
        tabLayout.getTabAt(3).setIcon(R.drawable.dem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaoThucAdapter.REQUEST_TIME && resultCode == PickTime.RESULT_CODE && data != null) {
            int Gio = data.getIntExtra(PickTime.HOUR, 0);
            int Phut = data.getIntExtra(PickTime.MINUTE, 0);
            FragmentBaoThuc fragmentBaoThuc = (FragmentBaoThuc) adapter.getFragmentByPosition(FragmentBaoThuc.TAB_POSITION);
            fragmentBaoThuc.setGioPhut(Gio, Phut);
            fragmentBaoThuc.refreshAdapter();
            fragmentBaoThuc.updateAlarm();
        }
        if (requestCode == BaoThucAdapter.REQUEST_CHON_NHAC && resultCode == ChonNhac.RESULT_CHON_NHAC && data != null) {
            int idRes = data.getIntExtra(ChonNhac.ID_RES, R.raw.apple);
            FragmentBaoThuc fragmentBaoThuc = (FragmentBaoThuc) adapter.getFragmentByPosition(FragmentBaoThuc.TAB_POSITION);
            fragmentBaoThuc.setNhacChuong(idRes);
            fragmentBaoThuc.refreshAdapter();
            fragmentBaoThuc.updateAlarm();
        }
        if (requestCode == FragmentBaoThuc.REQUEST_ADD && resultCode == PickTime.RESULT_CODE && data != null) {
            int Gio = data.getIntExtra(PickTime.HOUR, 0);
            int Phut = data.getIntExtra(PickTime.MINUTE, 0);
            FragmentBaoThuc fragmentBaoThuc = (FragmentBaoThuc) adapter.getFragmentByPosition(FragmentBaoThuc.TAB_POSITION);
            BaoThuc newBaoThuc = new BaoThuc(Gio, Phut, false, false, false, R.raw.apple);
            fragmentBaoThuc.addBaoThuc(newBaoThuc);
            fragmentBaoThuc.refreshAdapter();
            fragmentBaoThuc.updateAlarm();
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        }
    }
}
