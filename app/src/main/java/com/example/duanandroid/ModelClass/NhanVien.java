package com.example.duanandroid.ModelClass;

public class NhanVien {
    private String TenTaiKhoan;
    private String TenNhanVien;
    private String MatKhau;
    private String sdt;
    private String QueQuan;
    private String TrangThai;

    public NhanVien(String tenTaiKhoan, String tenNhanVien, String matKhau, String sdt, String queQuan, String trangThai) {
        TenTaiKhoan = tenTaiKhoan;
        TenNhanVien = tenNhanVien;
        MatKhau = matKhau;
        this.sdt = sdt;
        QueQuan = queQuan;
        TrangThai = trangThai;
    }

    public NhanVien() {
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        TenNhanVien = tenNhanVien;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String queQuan) {
        QueQuan = queQuan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
