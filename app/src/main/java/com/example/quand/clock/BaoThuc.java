package com.example.quand.clock;

import java.io.Serializable;

/**
 * Created by quand on 26-Mar-18.
 */

public class BaoThuc implements Serializable{
    private int ID;
    private int Gio;
    private int Phut;
    private Boolean Rung;
    private Boolean LapLai;
    private Boolean On;
    private int NhacChuong;

    public BaoThuc(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getGio() {
        return Gio;
    }

    public void setGio(int gio) {
        Gio = gio;
    }

    public int getPhut() {
        return Phut;
    }

    public void setPhut(int phut) {
        Phut = phut;
    }

    public Boolean getRung() {
        return Rung;
    }

    public void setRung(Boolean rung) {
        Rung = rung;
    }

    public Boolean getLapLai() {
        return LapLai;
    }

    public void setLapLai(Boolean lapLai) {
        LapLai = lapLai;
    }

    public Boolean getOn() {
        return On;
    }

    public void setOn(Boolean on) {
        On = on;
    }

    public int getNhacChuong() {
        return NhacChuong;
    }

    public void setNhacChuong(int nhacChuong) {
        NhacChuong = nhacChuong;
    }

    public BaoThuc(int gio, int phut, Boolean rung, Boolean lapLai, Boolean on, int nhacChuong) {
        Gio = gio;
        Phut = phut;
        Rung = rung;
        LapLai = lapLai;
        On = on;
        NhacChuong = nhacChuong;

    }
}
