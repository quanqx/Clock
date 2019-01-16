package com.example.quand.clock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by quand on 27-Mar-18.
 */

public class BaoDong extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar skStop;
    private BaoThuc baoThuc;
    private Vibrator vibrator;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baodong_layout);

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK|
                PowerManager.ACQUIRE_CAUSES_WAKEUP|
                PowerManager.ON_AFTER_RELEASE,"tag");
        wl.acquire();

        DBManager db = new DBManager(this, DBManager.DB_NAME, null, 1);
        baoThuc = (BaoThuc) getIntent().getSerializableExtra("current");
        skStop = findViewById(R.id.skStop);
        mediaPlayer = MediaPlayer.create(this, baoThuc.getNhacChuong());
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Báo thức")
                .setContentText(baoThuc.getGio()+":"+baoThuc.getPhut())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(6);

        notificationManager.notify(1, builder.build());

        if (!baoThuc.getLapLai()) {
            baoThuc.setOn(false);
            db.editBaoThuc(baoThuc);
        }
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timer = new Timer();
        if (baoThuc.getRung()) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    vibrator.vibrate(500);
                }
            }, 0, 3000);
        }
        skStop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (skStop.getProgress() >= 99) {
                    finish();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void finish() {
        timer.cancel();
        if (!baoThuc.getLapLai()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        super.finish();
    }
}
