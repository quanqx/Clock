package com.example.quand.clock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by quand on 27-Mar-18.
 */

public class FragmentBamGio extends Fragment {

    public static final int TAB_POSITION = 3;

    private ProgressBar progressBar;
    private FloatingActionButton fabStart, fabStop;
    private ImageButton btnAdd, btnReset;
    private ListView listView;
    private TextView txtH, txtM, txtS, txtMili;
    private Timer timer;
    private int h,m,s, mili;
    private boolean flagPause = false;
    private ArrayList<String> lst;
    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bamgio, container, false);

        initComps(view);

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabStart.setVisibility(View.INVISIBLE);
                fabStop.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                start();
            }
        });
        fabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagPause = !flagPause;
                pause();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReset.setVisibility(View.GONE);
                reset();
            }
        });
        return view;
    }

    private void reset() {
        progressBar.setProgress(0);
        txtH.setText("00:");
        txtM.setText("00:");
        txtS.setText("00");
        txtMili.setText("00");
        flagPause = false;
        fabStart.setVisibility(View.VISIBLE);
        fabStop.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        lst.clear();
        adapter.notifyDataSetChanged();
        timer.cancel();
    }

    private void add() {
        lst.add(lst.size()+1+": "+txtH.getText()+""+txtM.getText()+""+txtS.getText()+":"+txtMili.getText());
        adapter.notifyDataSetChanged();
    }

    private void pause() {
        if(flagPause){
            btnAdd.setVisibility(View.INVISIBLE);
            timer.cancel();
        }
        else {
            btnAdd.setVisibility(View.VISIBLE);
            runTimer();
        }
    }

    private void runTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mili++;
                if(mili>99){
                    mili = 0;
                    s++;
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtS.setText(s<10?"0"+s:s+"");
                            }
                        });
                    }catch (Exception e){
                        reset();
                    }
                }
                if(s>59){
                    s = 0;
                    m++;
                    try{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtM.setText(m<10?"0"+m+":":m+"");
                            }
                        });
                    }
                    catch (Exception e){
                        reset();
                    }
                }
                if(m>59){
                    m=0;
                    h++;
                    try{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtH.setText(h<10?"0"+h+":":h+"");
                            }
                        });
                    }
                    catch (Exception e){
                        reset();
                    }
                }
                try{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtMili.setText(mili+"");
                        }
                    });
                }catch (Exception e){
                    reset();
                }
            }
        }, 0, 10);
    }

    private void start() {
        mili = 0;
        s = 0;
        m = 0;
        h = 0;
        runTimer();
    }

    private void initComps(View view) {
        progressBar = view.findViewById(R.id.progressBarBamGio);
        fabStart = view.findViewById(R.id.fabStartBamGio);
        fabStop = view.findViewById(R.id.fabStopBamGio);
        btnAdd = view.findViewById(R.id.btnAddBamGio);
        btnReset = view.findViewById(R.id.btnResetBamGio);
        listView = view.findViewById(R.id.lstBamGio);
        txtH = view.findViewById(R.id.txtH);
        txtM = view.findViewById(R.id.txtM);
        txtS = view.findViewById(R.id.txtS);
        txtMili = view.findViewById(R.id.txtMili);
        fabStop.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        lst = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, lst);
        listView.setAdapter(adapter);
    }
}
