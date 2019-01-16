package com.example.quand.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by quand on 28-Mar-18.
 */

public class ChonNhac extends AppCompatActivity {

    private ListView lvChonNhac;
    private ArrayList<String> lstNhac;
    public static final String ID_RES = "idRes";
    public static final int RESULT_CHON_NHAC = 12345;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_nhac);
        lstNhac = new ArrayList<>();
        lvChonNhac = findViewById(R.id.lvChonNhac);
        lstNhac.add("Apple tone");
        lstNhac.add("Chim hót");
        lstNhac.add("Ngày mai nắng lên....");
        lstNhac.add("Samsung");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lstNhac);
        lvChonNhac.setAdapter(adapter);
        lvChonNhac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                int idRes = 0;
                switch (i){
                    case 0:
                        idRes = R.raw.apple;
                        break;
                    case 1:
                        idRes = R.raw.chimhot;
                        break;
                    case 2:
                        idRes = R.raw.nhac;
                        break;
                    case 3:
                        idRes = R.raw.samsung;
                        break;
                }
                intent.putExtra(ID_RES, idRes);
                setResult(RESULT_CHON_NHAC, intent);
                finish();
            }
        });
    }
}
