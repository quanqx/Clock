package com.example.quand.clock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by quand on 27-Mar-18.
 */

public class BaoThucAdapter extends BaseAdapter {

    private Activity activity;
    private int layout;
    private ArrayList<BaoThuc> lstBaoThuc;
    public final static int REQUEST_TIME = 123;
    public BaoThuc baoThuc, laplai;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private DBManager db;
    public BaoThuc currentBaoThuc;
    public final static int REQUEST_CHON_NHAC = 1234;
    public BaoThuc BaoThucChonNhac;

    public BaoThucAdapter(Activity activity, int layout, ArrayList arrayList) {
        this.activity = activity;
        this.layout = layout;
        this.lstBaoThuc = arrayList;
    }

    public void setDbManager(DBManager db) {
        this.db = db;
    }

    public void setListBaoThuc(ArrayList<BaoThuc> lstBaoThuc) {
        this.lstBaoThuc = lstBaoThuc;
    }

    @Override
    public int getCount() {
        return lstBaoThuc.size();
    }

    @Override
    public Object getItem(int i) {
        return lstBaoThuc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, null);
            holder = new ViewHolder();
            holder.txtThoiGian = view.findViewById(R.id.txtThoiGian);
            holder.btnSwitchOn = view.findViewById(R.id.btnSwitchOn);
            holder.btnNhacChuong = view.findViewById(R.id.btnNhacChuong);
            holder.btnXoa = view.findViewById(R.id.btnXoa);
            holder.chkLapLai = view.findViewById(R.id.chkLapLai);
            holder.chkRung = view.findViewById(R.id.chkRung);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final BaoThuc baothuc = lstBaoThuc.get(i);
        int hh = baothuc.getGio();
        int mm = baothuc.getPhut();
        String gio = "";
        String phut = "";
        if (hh < 10) gio = "0" + hh;
        else gio = "" + hh;
        if (mm < 10) phut = "0" + mm;
        else phut = "" + mm;
        holder.txtThoiGian.setText(gio + ":" + phut);
        holder.btnSwitchOn.setChecked(baothuc.getOn());
        holder.chkLapLai.setChecked(baothuc.getLapLai());
        holder.chkRung.setChecked(baothuc.getRung());
        String nc = " Nhạc chuông";
        if (baothuc.getNhacChuong() == R.raw.apple) nc = " Apple";
        if (baothuc.getNhacChuong() == R.raw.chimhot) nc = " Chim hót";
        if (baothuc.getNhacChuong() == R.raw.nhac) nc = " Ngày mai nắng lên....";
        if (baothuc.getNhacChuong() == R.raw.samsung) nc = " SamSung";
        holder.btnNhacChuong.setText(nc);
        holder.txtThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baoThuc = (BaoThuc) getItem(i);
                Intent intent = new Intent(activity, PickTime.class);
                activity.startActivityForResult(intent, REQUEST_TIME);
            }
        });
        holder.btnSwitchOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DBManager db = new DBManager(activity, DBManager.DB_NAME, null, 1);
                BaoThuc baoThuc = (BaoThuc) getItem(i);
                baoThuc.setOn(b);
                db.editBaoThuc(baoThuc);
                lstBaoThuc = db.getAll();
                notifyDataSetChanged();
                updateAlarm();
            }
        });
        holder.chkLapLai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                laplai = (BaoThuc) getItem(i);
                updateLaplai(b);
            }
        });
        holder.btnNhacChuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaoThucChonNhac = (BaoThuc) getItem(i);
                Intent intent = new Intent(activity, ChonNhac.class);
                activity.startActivityForResult(intent, REQUEST_CHON_NHAC);
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DBManager(activity, DBManager.DB_NAME, null, 1);
                BaoThuc baoThuc = (BaoThuc) getItem(i);
                db.deleteBaoThuc(baoThuc.getID());
                lstBaoThuc = db.getAll();
                notifyDataSetChanged();
                updateAlarm();
                Toast.makeText(activity, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.chkRung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                BaoThuc baoThuc = (BaoThuc) getItem(i);
                baoThuc.setRung(b);
                db.editBaoThuc(baoThuc);
                lstBaoThuc = db.getAll();
                notifyDataSetChanged();
                updateAlarm();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_listview);
        view.startAnimation(animation);
        return view;
    }

//    public void updateAlarm2() {
//        calendar = Calendar.getInstance();
//        ArrayList<BaoThuc> arrBaoThucOn = new ArrayList<>();
//        for (BaoThuc bt : lstBaoThuc) {
//            if (bt.getOn()) {
//                arrBaoThucOn.add(bt);
//            }
//        }
//        if (arrBaoThucOn.size() == 0) {
//            currentBaoThuc = null;
//            if (alarmManager != null)
//                alarmManager.cancel(pendingIntent);
//            return;
//        }
//        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
//        if (arrBaoThucOn.size() == 1) {
//            int gio = arrBaoThucOn.get(0).getGio();
//            int phut = arrBaoThucOn.get(0).getPhut();
//            calendar.set(Calendar.HOUR_OF_DAY, gio);
//            calendar.set(Calendar.MINUTE, phut);
//            calendar.set(Calendar.SECOND, 0);
//            if (gio > nowHour || (gio == nowHour && phut > nowMinute)) {
//                calendar.set(Calendar.DAY_OF_WEEK, nowDay);
//            } else {
//                calendar.set(Calendar.DAY_OF_WEEK, nowDay + 1);
//            }
//            currentBaoThuc = arrBaoThucOn.get(0);
//            datGio();
//            return;
//        }
//        BaoThuc baoThuc = arrBaoThucOn.get(0);
//        currentBaoThuc = baoThuc;
//        calendar.set(Calendar.HOUR_OF_DAY, baoThuc.getGio());
//        calendar.set(Calendar.MINUTE, baoThuc.getPhut());
//        if (baoThuc.getGio() > nowHour || (baoThuc.getGio() == nowHour && baoThuc.getPhut() > nowMinute)) {
//            calendar.set(Calendar.DAY_OF_WEEK, nowDay);
//        } else {
//            calendar.set(Calendar.DAY_OF_WEEK, nowDay + 1);
//        }
//        for (BaoThuc bt : arrBaoThucOn) {
//            Calendar temp = Calendar.getInstance();
//            int h = bt.getGio();
//            int m = bt.getPhut();
//            temp.set(Calendar.HOUR_OF_DAY, h);
//            temp.set(Calendar.MINUTE, m);
//            if (h > nowHour || (h == nowHour && m > nowMinute)) {
//                temp.set(Calendar.DAY_OF_WEEK, nowDay);
//            } else {
//                temp.set(Calendar.DAY_OF_WEEK, nowDay + 1);
//            }
//            if (calendar.getTimeInMillis() > temp.getTimeInMillis()) {
//                calendar.set(Calendar.HOUR_OF_DAY, h);
//                calendar.set(Calendar.MINUTE, m);
//                calendar.set(Calendar.DAY_OF_WEEK, temp.get(Calendar.DAY_OF_WEEK));
//                calendar.set(Calendar.SECOND, 0);
//                currentBaoThuc = bt;
//            }
//        }
//        datGio();
//    }

    public void updateAlarm(){
        calendar = Calendar.getInstance();
        int nowD = calendar.get(Calendar.DAY_OF_WEEK);
        int nowH = calendar.get(Calendar.HOUR_OF_DAY);
        int nowM = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.SECOND, 0);
        ArrayList<BaoThuc> lstOn = new ArrayList<>();
        for (BaoThuc bt:lstBaoThuc) {
            if(bt.getOn()) lstOn.add(bt);
        }
        if(lstOn.size()==0){
            if(alarmManager != null)
                alarmManager.cancel(pendingIntent);
            return;
        }
        if(lstOn.size()==1){
            Log.d("ListOn", "size = 1");
            currentBaoThuc = lstOn.get(0);
            int h = currentBaoThuc.getGio();
            int m = currentBaoThuc.getPhut();
            calendar.set(Calendar.HOUR_OF_DAY, h);
            calendar.set(Calendar.MINUTE, m);
            if(nowH>h||(nowH==h&&nowM<m)){
                calendar.set(Calendar.DAY_OF_WEEK, nowD);
            }
            else{
                calendar.set(Calendar.DAY_OF_WEEK, nowD + 1);
            }
            datGio();
            return;
        }
        Log.d("ListOn", "size > 2");
        currentBaoThuc = lstOn.get(0);
        calendar.set(Calendar.HOUR_OF_DAY, currentBaoThuc.getGio());
        calendar.set(Calendar.MINUTE, currentBaoThuc.getPhut());
        calendar.set(Calendar.SECOND, 0);
        for (BaoThuc bt:lstOn) {
            Calendar temp = Calendar.getInstance();
            int h = bt.getGio();
            int m = bt.getPhut();
            temp.set(Calendar.HOUR_OF_DAY, h);
            temp.set(Calendar.MINUTE, m);
            if(nowH>h||(nowH==h&&nowM<m)){
                temp.set(Calendar.DAY_OF_WEEK, nowD);
            }
            else{
                temp.set(Calendar.DAY_OF_WEEK, nowD + 1);
            }
            if(calendar.getTimeInMillis()>temp.getTimeInMillis()){
                calendar.set(Calendar.DAY_OF_WEEK, temp.get(Calendar.DAY_OF_WEEK));
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);
                calendar.set(Calendar.SECOND, 0);
                currentBaoThuc = bt;
            }
        }
        Log.d(""+currentBaoThuc.getGio()+":"+currentBaoThuc.getPhut(), Calendar.getInstance().getTimeInMillis()+" : "+calendar.getTimeInMillis());
        datGio();
    }

    private void datGio() {
        alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.putExtra("current", currentBaoThuc);
        pendingIntent = PendingIntent.getBroadcast(activity, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private class ViewHolder {
        TextView txtThoiGian;
        Switch btnSwitchOn;
        Button btnNhacChuong, btnXoa;
        CheckBox chkRung, chkLapLai;
    }

    private void updateLaplai(boolean LapLai) {
        db = new DBManager(activity, DBManager.DB_NAME, null, 1);
        laplai.setLapLai(LapLai);
        db.editBaoThuc(laplai);
        lstBaoThuc = db.getAll();
        notifyDataSetChanged();
    }

    void setNhacChuongResId(int resId) {
        BaoThucChonNhac.setNhacChuong(resId);
        db.editBaoThuc(BaoThucChonNhac);
    }

    void setGioPhut(int gio, int phut) {
        baoThuc.setGio(gio);
        baoThuc.setPhut(phut);
        db.editBaoThuc(baoThuc);
    }

}
