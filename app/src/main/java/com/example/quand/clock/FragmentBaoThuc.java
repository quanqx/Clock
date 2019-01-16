package com.example.quand.clock;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by quand on 27-Mar-18.
 */

public class FragmentBaoThuc extends Fragment {

    public static final int TAB_POSITION = 0;

    private BaoThucAdapter adapter;
    private ArrayList<BaoThuc> lstBaoThuc;
    private ListView listView;
    private DBManager database;
    private FloatingActionButton btnAdd;
    public final static int REQUEST_ADD = 11111;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_baothuc, container, false);
        initComps(view);
        return view;
    }

    private void initComps(View view) {
        database = new DBManager(getActivity(), DBManager.DB_NAME, null,1);
        listView = view.findViewById(R.id.listView);
        lstBaoThuc = database.getAll();
        adapter = new BaoThucAdapter(getActivity(), R.layout.custom_listview, lstBaoThuc);
        adapter.setDbManager(database);
        listView.setAdapter(adapter);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBaoThuc();
            }
        });
    }

    private void addBaoThuc(){
        Intent intent = new Intent(getActivity(), PickTime.class);
        getActivity().startActivityForResult(intent, REQUEST_ADD);
    }

    public void refreshAdapter(){
        lstBaoThuc = database.getAll();
        adapter.setListBaoThuc(lstBaoThuc);
        adapter.notifyDataSetChanged();
    }

    public void setNhacChuong(int resId) {
        adapter.setNhacChuongResId(resId);
    }

    public void setGioPhut(int gio, int phut) {
        adapter.setGioPhut(gio, phut);
    }

    public void updateAlarm() {
        adapter.updateAlarm();
    }

    public void addBaoThuc(BaoThuc baoThuc) {
        database.addBaoThuc(baoThuc);
    }

}
