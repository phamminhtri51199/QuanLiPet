package com.example.duanandroid.Sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanandroid.ModelClass.KhachHang;

import java.util.ArrayList;

public class KhachHangSql {
    Sqlite sqlite;

    public KhachHangSql(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    public int ThemKhachHang(KhachHang khachHang) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("makh", khachHang.getMaKh());
        contentValues.put("tenkh", khachHang.getTenKh());
        contentValues.put("sdt", khachHang.getSdt());
        sqLiteDatabase.insert("khachhang", null, contentValues);
        return 1;
    }
    public ArrayList<KhachHang> LayAllKhachHang(){
        SQLiteDatabase sqLiteDatabase=sqlite.getReadableDatabase();
        String select="SELECT * FROM khachhang";
        ArrayList<KhachHang> arrKhachHang=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery(select,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                KhachHang khachHang=new KhachHang();
                khachHang.setMaKh(cursor.getString(0));
                khachHang.setTenKh(cursor.getString(1));
                khachHang.setSdt(cursor.getString(2));
                arrKhachHang.add(khachHang);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrKhachHang;
    }
    public KhachHang LayKhachHangTheoMa(String makh) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM khachhang WHERE makh=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{makh});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            KhachHang khachHang = new KhachHang();
            khachHang.setMaKh(cursor.getString(0));
            khachHang.setTenKh(cursor.getString(1));
            khachHang.setSdt(cursor.getString(2));
            cursor.close();
            return khachHang;
        } else {
            cursor.close();
            return null;
        }
    }

    public void XoaKhachHang(String maKhachHang) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        sqLiteDatabase.delete("khachhang", "makh=?", new String[]{maKhachHang});
    }

}
