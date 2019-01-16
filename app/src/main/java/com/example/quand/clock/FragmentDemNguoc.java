package com.example.quand.clock;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by quand on 27-Mar-18.
 */

public class FragmentDemNguoc extends Fragment {

    public static final int TAB_POSITION = 2;

    private ProgressBar progressBar;
    private EditText edtGio, edtPhut, edtGiay;
    private FloatingActionButton fabStart, fabReset;
    private TextView txtGiayDemNguoc;
    int count = 0, sumGiay = 0;
    private MediaPlayer mediaPlayer;
    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demnguoc, container, false);

        initComps(view);

        edtPhut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = edtPhut.getText().toString().trim();
                if(str.length() > 0){
                    int phut = Integer.parseInt(str+"");
                    if(phut>60){
                        edtPhut.setText(str.substring(0,str.length()-1));
                    }
                }
            }
        });
        edtGiay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = edtGiay.getText().toString().trim();
                if(str.length() > 0){
                    int giay = Integer.parseInt(str+"");
                    if(giay>60){
                        edtGiay.setText(str.substring(0,str.length()-1));
                    }
                }
            }
        });

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkTime()) return;
                if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                }
                if(timer != null){
                    timer.cancel();
                }
                CountDown();
            }
        });

        fabReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                if(timer != null){
                    timer.cancel();
                }
            }
        });

        return view;
    }

//    private void start() {
//        String strGio = edtGio.getText().toString().trim();
//        String strPhut = edtPhut.getText().toString().trim();
//        String strGiay = edtGiay.getText().toString().trim();
//        int gio = Integer.parseInt(strGio.length()==0?"0":strGio);
//        int phut = Integer.parseInt(strPhut.length()==0?"0":strPhut);
//        int giay = Integer.parseInt(strGiay.length()==0?"0":strGiay);
//        sumGiay = 60*60*gio+60*phut+giay;
//        progressBar.setMax(sumGiay);
//        count = 0;
//        txtGiayDemNguoc.setText(sumGiay+" s");
//        timer = new CountDownTimer(sumGiay*1000+2000, 1000) {
//            @Override
//            public void onTick(long l) {
//                progressBar.setProgress(count);
//                txtGiayDemNguoc.setText(sumGiay-count+" s");
//                count++;
//            }
//
//            @Override
//            public void onFinish() {
//                fabStart.setVisibility(View.VISIBLE);
//                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.samsung);
//                mediaPlayer.start();
//            }
//        }.start();
//    }

    private void reset(){
        fabStart.setVisibility(View.VISIBLE);
        edtGio.setText("");
        edtPhut.setText("");
        edtGiay.setText("");
        progressBar.setProgress(0);
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        txtGiayDemNguoc.setText("0 s");
        if(timer != null){
            timer.cancel();
        }
    }

    private void CountDown(){
        timer = new Timer();
        String strGio = edtGio.getText().toString().trim();
        String strPhut = edtPhut.getText().toString().trim();
        String strGiay = edtGiay.getText().toString().trim();
        int gio = Integer.parseInt(strGio.length()==0?"0":strGio);
        int phut = Integer.parseInt(strPhut.length()==0?"0":strPhut);
        int giay = Integer.parseInt(strGiay.length()==0?"0":strGiay);
        sumGiay = 60*60*gio+60*phut+giay;
        progressBar.setMax(sumGiay*100);
        count = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(count);
                count++;
                if(count%100 == 0){
                    if(sumGiay == 0){
                        if(getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fabStart.setVisibility(View.VISIBLE);
                                }
                            });
                            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.samsung);
                            mediaPlayer.start();
                            this.cancel();
                            return;
                        }
                        else{
                            reset();
                            return;
                        }

                    }
                    sumGiay-=1;
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtGiayDemNguoc.setText(sumGiay+" s");
                            }
                        });
                    }
//                    else{
//                        reset();
//                        return;
//                    }
                }
            }
        }, 0, 10);
    }

    private Boolean checkTime() {
        if(edtGiay.getText().toString().trim().length() == 0&&edtPhut.getText().toString().trim().length() == 0&&edtGio.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), "Hãy nhập vào thời gian!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initComps(View view){
        progressBar = view.findViewById(R.id.progressBar);
        edtGio = view.findViewById(R.id.edtGioDemNguoc);
        edtPhut = view.findViewById(R.id.edtPhutDemNguoc);
        edtGiay = view.findViewById(R.id.edtGiayDemNguoc);
        fabStart = view.findViewById(R.id.fabStart);
        fabReset = view.findViewById(R.id.fabReset);
        txtGiayDemNguoc = view.findViewById(R.id.txtGiayDemNguoc);
    }
}
