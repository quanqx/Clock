package com.example.quand.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by quand on 27-Mar-18.
 */

public class PickTime extends AppCompatActivity {

    private TimePicker timePicker;
    private Button btnOk, btnCancel;
    public final static String HOUR = "gio";
    public final static String MINUTE = "phut";
    public final static int RESULT_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_time);
        initComps();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(HOUR, timePicker.getCurrentHour());
                intent.putExtra(MINUTE, timePicker.getCurrentMinute());
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initComps() {
        timePicker = findViewById(R.id.timePicker);
        btnOk = findViewById(R.id.btnPickOk);
        btnCancel = findViewById(R.id.btnPickCancel);
    }
}
