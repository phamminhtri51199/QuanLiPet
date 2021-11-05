package com.example.duanandroid.Sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanandroid.ModelClass.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonCtSql {
    Sqlite sqlite;

    public HoaDonCtSql(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    public void ThemHdct(HoaDonChiTiet hdct) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahd", hdct.getMaHoaDon());
        contentValues.put("mapet", hdct.getMaPet());
        sqLiteDatabase.insert("hoadonct", null, contentValues);
    }

    public ArrayList<HoaDonChiTiet> LayAllHdct() {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        ArrayList<HoaDonChiTiet> arrHdct = new ArrayList<>();
        String select = "SELECT * FROM hoadonct";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHdct(Integer.toString(cursor.getInt(0)));
                hdct.setMaHoaDon(cursor.getString(1));
                hdct.setMaPet(cursor.getString(2));
                arrHdct.add(hdct);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrHdct;
    }

    public void XoaHdct(String maHdct) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        sqLiteDatabase.delete("hoadonct", "mahdct=?", new String[]{maHdct});
    }

    public ArrayList<HoaDonChiTiet> LayAllHdctTheoMaHd(String maHd) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM hoadonct WHERE mahd=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{maHd});
        ArrayList<HoaDonChiTiet> arrHdct = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                ;
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setMaHdct(Integer.toString(cursor.getInt(0)));
                hoaDonChiTiet.setMaHoaDon(cursor.getString(1));
                hoaDonChiTiet.setMaPet(cursor.getString(2));
                arrHdct.add(hoaDonChiTiet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrHdct;
    }
}
