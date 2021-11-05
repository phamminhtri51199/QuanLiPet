package com.example.duanandroid.Sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanandroid.ModelClass.NhanVien;

import java.util.ArrayList;

public class NhanVienSql {
    Sqlite sql;

    public NhanVienSql(Sqlite sqlite) {
        this.sql = sqlite;
    }

    public long addNhanVien(NhanVien nhanVien) {
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tentk", nhanVien.getTenTaiKhoan());
        contentValues.put("tennv", nhanVien.getTenNhanVien());
        contentValues.put("mk", nhanVien.getMatKhau());
        contentValues.put("sdt", nhanVien.getSdt());
        contentValues.put("quequan", nhanVien.getQueQuan());
        contentValues.put("trangthai","active");
        long listNhanVien = sqLiteDatabase.insert("nhanvien", null, contentValues);
        return listNhanVien;
    }

    public ArrayList<NhanVien> getAllNhanVien() {
        SQLiteDatabase sqLiteDatabase = sql.getReadableDatabase();
        String select = "SELECT * FROM nhanvien ";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        ArrayList<NhanVien> arrNhanVien = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setTenTaiKhoan(cursor.getString(0));
                nhanVien.setTenNhanVien(cursor.getString(1));
                nhanVien.setMatKhau(cursor.getString(2));
                nhanVien.setSdt(cursor.getString(3));
                nhanVien.setQueQuan(cursor.getString(4));
                nhanVien.setTrangThai(cursor.getString(5));
                arrNhanVien.add(nhanVien);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrNhanVien;
    }
    public String layMkTheoTk(String tk){
        SQLiteDatabase sqLiteDatabase=sql.getReadableDatabase();
        String select="SELECT mk FROM nhanvien WHERE tentk=?";
        Cursor cursor=sqLiteDatabase.rawQuery(select,new String[]{tk});
        String mk="";
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            mk=cursor.getString(0);
        }
        cursor.close();
        return mk;
    }

    public void UpdateNvActive(String tentk){
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai","active");
        sqLiteDatabase.update("nhanvien",contentValues,"tentk=?",new String[]{tentk});
    }
    public void UpdateNvInactive(String tentk){
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai","inactive");
        sqLiteDatabase.update("nhanvien",contentValues,"tentk=?",new String[]{tentk});
    }
    public void UpdateNhanVien(String tentk, String tennv, String mk, String sdt, String queQuan){
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tennv", tennv);
        contentValues.put("mk", mk);
        contentValues.put("sdt", sdt);
        contentValues.put("quequan", queQuan);
        sqLiteDatabase.update("nhanvien",contentValues,"tentk=?",new String[]{tentk});
    }
}
