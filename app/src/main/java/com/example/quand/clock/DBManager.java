package com.example.quand.clock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by quand on 27-Mar-18.
 */

public class DBManager extends SQLiteOpenHelper {

    public static String DB_NAME = "Baothuc4.sqlite";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table if not exists BaoThuc("+
                "ID integer primary key autoincrement, "+
                "Gio integer,"+
                "Phut integer,"+
                "Rung boolean,"+
                "LapLai boolean,"+
                "Bat boolean,"+
                "NhacChuong int)";
        sqLiteDatabase.execSQL(sql);
        sql = "Insert into BaoThuc values(null, 12, 10, 0, 0, 0, 1)";
        sqLiteDatabase.execSQL(sql);
        sql = "Insert into BaoThuc values(null, 11, 11, 0, 0, 0, 1)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addBaoThuc(BaoThuc baoThuc){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "Insert into BaoThuc values(null,"+
                baoThuc.getGio()+","+
                baoThuc.getPhut()+","+
                (baoThuc.getRung()?"1":"0")+","+
                (baoThuc.getLapLai()?"1":"0")+","+
                (baoThuc.getOn()?"1":"0")+","+
                baoThuc.getNhacChuong()+")";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void editBaoThuc(BaoThuc baoThuc){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "Update BaoThuc set Gio = "+baoThuc.getGio()+
                ",Phut = "+baoThuc.getPhut()+
                ",Rung = "+(baoThuc.getRung()?"1":"0")+
                ",LapLai = "+(baoThuc.getLapLai()?"1":"0")+
                ",Bat = "+(baoThuc.getOn()?"1":"0")+
                ",NhacChuong = "+baoThuc.getNhacChuong()+
                " where ID = "+baoThuc.getID();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void deleteBaoThuc(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "Delete from BaoThuc where ID = "+id;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public ArrayList<BaoThuc> getAll(){
        ArrayList<BaoThuc> lstBaoThuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "Select * from BaoThuc";
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        if(cur.moveToFirst()){
            do{
                BaoThuc baoThuc = new BaoThuc();
                baoThuc.setID(cur.getInt(0));
                baoThuc.setGio(cur.getInt(1));
                baoThuc.setPhut(cur.getInt(2));
                baoThuc.setRung((cur.getInt(3)==1)?true:false);
                baoThuc.setLapLai((cur.getInt(4)==1)?true:false);
                baoThuc.setOn((cur.getInt(5)==1)?true:false);
                baoThuc.setNhacChuong(cur.getInt(6));
                lstBaoThuc.add(baoThuc);
            }
            while(cur.moveToNext());
        }
        cur.close();
        sqLiteDatabase.close();
        return lstBaoThuc;
    }

    public BaoThuc findByID(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "Select * from BaoThuc where ID = " + id;
        Cursor cur = sqLiteDatabase.rawQuery(sql, null);
        BaoThuc baoThuc = new BaoThuc();
        if(cur!=null){
            cur.moveToFirst();
            baoThuc.setID(cur.getInt(0));
            baoThuc.setGio(cur.getInt(1));
            baoThuc.setPhut(cur.getInt(2));
            baoThuc.setRung((cur.getInt(3)==1)?true:false);
            baoThuc.setLapLai((cur.getInt(4)==1)?true:false);
            baoThuc.setOn((cur.getInt(5)==1)?true:false);
            baoThuc.setNhacChuong(cur.getInt(6));

        }
        sqLiteDatabase.close();
        cur.close();
        return  baoThuc;
    }
}
