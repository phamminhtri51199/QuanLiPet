package com.example.duanandroid.ModelClass;

public class HoaDonChiTiet {
    private String maHdct;
    private String maHoaDon;
    private String maPet;

    public HoaDonChiTiet() {
    }
    
    public HoaDonChiTiet(String maHdct, String maHoaDon, String maPet) {
        this.maHdct = maHdct;
        this.maHoaDon = maHoaDon;
        this.maPet = maPet;
    }

    public String getMaHdct() {
        return maHdct;
    }

    public void setMaHdct(String maHdct) {
        this.maHdct = maHdct;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaPet() {
        return maPet;
    }

    public void setMaPet(String maPet) {
        this.maPet = maPet;
    }
}
