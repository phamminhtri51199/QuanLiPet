package com.example.duanandroid.Sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanandroid.ModelClass.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonSql {
    Sqlite sqlite;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonSql(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    public int ThemHoaDon(HoaDon hoaDon) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahoadon", hoaDon.getMaHoaDon());
        contentValues.put("tentk", hoaDon.getTenTk());
        contentValues.put("makh", hoaDon.getMaKh());
        contentValues.put("ngaymua", simpleDateFormat.format(hoaDon.getNgayMua()));
        contentValues.put("tongtien",hoaDon.getTongTien());
        sqLiteDatabase.insert("hoadon", null, contentValues);
        return 1;
    }

    public ArrayList<HoaDon> LayAllHoaDon() throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM hoadon";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        ArrayList<HoaDon> arrHoaDon = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(cursor.getString(0));
                hoaDon.setTenTk(cursor.getString(1));
                hoaDon.setMaKh(cursor.getString(2));
                hoaDon.setNgayMua(simpleDateFormat.parse(cursor.getString(3)));
                hoaDon.setTongTien(cursor.getInt(4));
                arrHoaDon.add(hoaDon);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrHoaDon;
    }
    public ArrayList<HoaDon> LayHoaDon(String mahoadon) throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM hoadon WHERE mahoadon=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{mahoadon});
        ArrayList<HoaDon> arrHoaDon = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(cursor.getString(0));
                hoaDon.setTenTk(cursor.getString(1));
                hoaDon.setMaKh(cursor.getString(2));
                hoaDon.setNgayMua(simpleDateFormat.parse(cursor.getString(3)));
                hoaDon.setTongTien(cursor.getInt(4));
                arrHoaDon.add(hoaDon);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrHoaDon;
    }
    public ArrayList<HoaDon> LayHoaDonTheoThang(String thang) throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM hoadon WHERE strftime('%m',hoadon.ngaymua)=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{thang});
        ArrayList<HoaDon> arrHoaDon = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(cursor.getString(0));
                hoaDon.setTenTk(cursor.getString(1));
                hoaDon.setMaKh(cursor.getString(2));
                hoaDon.setNgayMua(simpleDateFormat.parse(cursor.getString(3)));
                hoaDon.setTongTien(cursor.getInt(4));
                arrHoaDon.add(hoaDon);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrHoaDon;
    }

    public int LayTongTien(String mahd) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        int tongtien = 0;
//        String select_tongtien = "SELECT SUM(giatien) FROM vatnuoi " +
//                "INNER JOIN hoadonct ON vatnuoi.mavatnuoi=hoadonct.mavatnuoi " +
//                "INNER JOIN hoadon ON hoadon.mahoadon=hoadonct.mahd WHERE mahd=?";
        String select_tongtien = "SELECT tongtien FROM hoadon WHERE mahoadon=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select_tongtien, new String[]{mahd});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tongtien = cursor.getInt(0);
        }
        cursor.close();
        return tongtien;
    }

    public int LayDoanhThuTheoThang(String thang) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        int doanhthu = 0;
//        String select_doanhthu = "SELECT SUM(giatien) FROM vatnuoi " +
//                "INNER JOIN hoadonct ON vatnuoi.mavatnuoi=hoadonct.mavatnuoi " +
//                "INNER JOIN hoadon ON hoadon.mahoadon=hoadonct.mahd WHERE strftime('%m',hoadon.ngaymua)=?";
        String select_doanhthu = "SELECT SUM(tongtien) FROM hoadon WHERE strftime('%m',hoadon.ngaymua)=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select_doanhthu, new String[]{thang});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            doanhthu = cursor.getInt(0);
        }
        cursor.close();
        return doanhthu;
    }

    public void XoaHoaDon(String mahoadon){
        SQLiteDatabase sqLiteDatabase=sqlite.getReadableDatabase();
        sqLiteDatabase.delete("hoadon","mahoadon=?",new String[]{mahoadon});
    }

    public void UpdateTongTien(int tongtien, String mahd){
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tongtien",tongtien);
        sqLiteDatabase.update("hoadon",contentValues,"mahoadon=?",new String[]{mahd});
    }
}
