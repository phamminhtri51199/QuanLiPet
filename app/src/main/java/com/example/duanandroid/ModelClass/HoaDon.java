package com.example.duanandroid.ModelClass;

import java.util.Date;

public class HoaDon {
    private String maHoaDon;
    private String tenTk;
    private String maKh;
    private Date ngayMua;
    private int tongTien;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String tenTk, String maKh, Date ngayMua, int tongTien) {
        this.maHoaDon = maHoaDon;
        this.tenTk = tenTk;
        this.maKh = maKh;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenTk() {
        return tenTk;
    }

    public void setTenTk(String tenTk) {
        this.tenTk = tenTk;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
