package com.example.duanandroid.ModelClass;

public class KhachHang {
    private String maKh;
    private String tenKh;
    private String sdt;

    public KhachHang() {
    }

    public KhachHang(String maKh, String tenKh, String sdt) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sdt = sdt;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
