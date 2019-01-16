package com.example.quand.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by quand on 27-Mar-18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        BaoThuc baoThuc = (BaoThuc) intent.getSerializableExtra("current");
        Intent intent1 = new Intent(context, AlarmService.class);
        intent1.putExtra("current", baoThuc);
        context.startService(intent1);
    }
}
