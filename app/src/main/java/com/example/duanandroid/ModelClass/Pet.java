package com.example.duanandroid.ModelClass;

import java.util.Date;

public class Pet {
    private String maPet;
    private String tenPet;
    private String tenLoai;
    private String gioiTinh;
    private String tuoi;
    private String moTa;
    private byte[] anh;
    private Date ngayNhap;
    private int giaTien;
    private String trangThai;

    public Pet() {
    }

    public Pet(String maPet, String tenPet, String tenLoai, String gioiTinh, String tuoi, String moTa, byte[] anh, Date ngayNhap, int giaTien, String trangThai) {
        this.maPet = maPet;
        this.tenPet = tenPet;
        this.tenLoai = tenLoai;
        this.gioiTinh = gioiTinh;
        this.tuoi = tuoi;
        this.moTa = moTa;
        this.anh = anh;
        this.ngayNhap = ngayNhap;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaPet() {
        return maPet;
    }

    public void setMaPet(String maPet) {
        this.maPet = maPet;
    }

    public String getTenPet() {
        return tenPet;
    }

    public void setTenPet(String tenPet) {
        this.tenPet = tenPet;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }
}
