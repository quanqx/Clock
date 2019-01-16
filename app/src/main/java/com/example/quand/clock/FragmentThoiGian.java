package com.example.quand.clock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by quand on 27-Mar-18.
 */

public class FragmentThoiGian extends android.support.v4.app.Fragment {

    public static final int TAB_POSITION = 1;
    private TextView txtNTN;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thoigian, container, false);
        txtNTN = view.findViewById(R.id.txtNTN);
//        txtHPS = view.findViewById(R.id.txtHPS);
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Calendar c = Calendar.getInstance();
//                        int seconds = c.get(Calendar.SECOND);
//                        int minutes = c.get(Calendar.MINUTE);
//                        int hour = c.get(Calendar.HOUR_OF_DAY);
//                        String time = (hour<10?"0"+hour:""+hour) + ":" + (minutes<10?"0"+minutes:""+minutes) + ":" + (seconds<10?"0"+seconds:""+seconds);
//                        txtHPS.setText(time);
//                    }
//                });
//            }
//        },0,1000);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = (day<10?"0"+day:""+day) + "/" + (month<10?"0"+(month+1):""+month+1) + "/" + (year<10?"0"+year:""+year);
        txtNTN.setText(date);
        return view;
    }
}
