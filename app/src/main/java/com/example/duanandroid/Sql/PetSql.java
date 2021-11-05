package com.example.duanandroid.Sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.duanandroid.ModelClass.Pet;

import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PetSql {
    Sqlite sqlite;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PetSql(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    public int ThemVatNuoi(String maPet, String tenPet, String tenLoai, String gioiTinh, String tuoi, String moTa, byte[] anh, Date ngayNhap, int giaTien, String trangThai) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String insert = "INSERT INTO pet VALUES(?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(insert);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, maPet);
        sqLiteStatement.bindString(2, tenPet);
        sqLiteStatement.bindString(3, tenLoai);
        sqLiteStatement.bindString(4, gioiTinh);
        sqLiteStatement.bindString(5, tuoi);
        sqLiteStatement.bindString(6, moTa);
        sqLiteStatement.bindBlob(7, anh);
        sqLiteStatement.bindString(8, simpleDateFormat.format(ngayNhap));
        sqLiteStatement.bindLong(9, giaTien);
        sqLiteStatement.bindString(10, trangThai);
        sqLiteStatement.executeInsert();
        return 1;
    }

    public ArrayList<Pet> LayAllPet() throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        ArrayList<Pet> arrPet = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Pet pet = new Pet();
                pet.setMaPet(cursor.getString(0));
                pet.setTenPet(cursor.getString(1));
                pet.setTenLoai(cursor.getString(2));
                pet.setGioiTinh(cursor.getString(3));
                pet.setTuoi(cursor.getString(4));
                pet.setMoTa(cursor.getString(5));
                pet.setAnh(cursor.getBlob(6));
                pet.setNgayNhap(simpleDateFormat.parse(cursor.getString(7)));
                pet.setGiaTien(cursor.getInt(8));
                pet.setTrangThai(cursor.getString(9));
                arrPet.add(pet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrPet;
    }
    public ArrayList<Pet> LayAllPetTheoThang(String thang) throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet WHERE strftime('%m',pet.ngaynhap)=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{thang});
        ArrayList<Pet> arrPet = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Pet pet = new Pet();
                pet.setMaPet(cursor.getString(0));
                pet.setTenPet(cursor.getString(1));
                pet.setTenLoai(cursor.getString(2));
                pet.setGioiTinh(cursor.getString(3));
                pet.setTuoi(cursor.getString(4));
                pet.setMoTa(cursor.getString(5));
                pet.setAnh(cursor.getBlob(6));
                pet.setNgayNhap(simpleDateFormat.parse(cursor.getString(7)));
                pet.setGiaTien(cursor.getInt(8));
                pet.setTrangThai(cursor.getString(9));
                arrPet.add(pet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrPet;
    }

    public ArrayList<Pet> LayAllCho() throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet WHERE tenloai=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{"Chó"});
        ArrayList<Pet> arrPet = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Pet pet = new Pet();
                pet.setMaPet(cursor.getString(0));
                pet.setTenPet(cursor.getString(1));
                pet.setTenLoai(cursor.getString(2));
                pet.setGioiTinh(cursor.getString(3));
                pet.setTuoi(cursor.getString(4));
                pet.setMoTa(cursor.getString(5));
                pet.setAnh(cursor.getBlob(6));
                pet.setNgayNhap(simpleDateFormat.parse(cursor.getString(7)));
                pet.setGiaTien(cursor.getInt(8));
                pet.setTrangThai(cursor.getString(9));
                arrPet.add(pet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrPet;
    }

    public ArrayList<Pet> LayAllMeo() throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet WHERE tenloai=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{"Mèo"});
        ArrayList<Pet> arrPet = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Pet pet = new Pet();
                pet.setMaPet(cursor.getString(0));
                pet.setTenPet(cursor.getString(1));
                pet.setTenLoai(cursor.getString(2));
                pet.setGioiTinh(cursor.getString(3));
                pet.setTuoi(cursor.getString(4));
                pet.setMoTa(cursor.getString(5));
                pet.setAnh(cursor.getBlob(6));
                pet.setNgayNhap(simpleDateFormat.parse(cursor.getString(7)));
                pet.setGiaTien(cursor.getInt(8));
                pet.setTrangThai(cursor.getString(9));
                arrPet.add(pet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrPet;
    }

    public Pet LayPetTheoMa(String maPet) throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet WHERE mapet=?";
        Cursor cursor = sqLiteDatabase.rawQuery(select, new String[]{maPet});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Pet pet = new Pet();
            pet.setMaPet(cursor.getString(0));
            pet.setTenPet(cursor.getString(1));
            pet.setTenLoai(cursor.getString(2));
            pet.setGioiTinh(cursor.getString(3));
            pet.setTuoi(cursor.getString(4));
            pet.setMoTa(cursor.getString(5));
            pet.setAnh(cursor.getBlob(6));
            pet.setNgayNhap(simpleDateFormat.parse(cursor.getString(7)));
            pet.setGiaTien(cursor.getInt(8));
            pet.setTrangThai(cursor.getString(9));
            cursor.close();
            return pet;
        } else {
            cursor.close();
            return null;
        }
    }

    public void XoaPet(String maPet) {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        sqLiteDatabase.delete("pet", "mapet=?", new String[]{maPet});
    }

    public ArrayList<String> LayAllMaPet() throws ParseException {
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "SELECT * FROM pet";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        ArrayList<String> arrMaPet = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String maPet = new String();
                maPet = cursor.getString(0);
                arrMaPet.add(maPet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrMaPet;
    }

    public void UpdateTrangThai(String maPet) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement("UPDATE pet SET trangthai='Đã bán' WHERE mapet=?");
        sqLiteStatement.bindString(1, maPet);
        sqLiteStatement.execute();
    }

    public void UpdatePet(String mapet, String tenPet, String tenLoai, String gioiTinh, String tuoi, String moTa, byte[] anh, Date ngayNhap, int giaTien){
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement("UPDATE pet SET tenpet=?, tenloai=?, gioitinh=?, tuoi=?, mota=?, anh=?, ngaynhap=?, giatien=? WHERE mapet=?");
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, tenPet);
        sqLiteStatement.bindString(2, tenLoai);
        sqLiteStatement.bindString(3, gioiTinh);
        sqLiteStatement.bindString(4, tuoi);
        sqLiteStatement.bindString(5, moTa);
        sqLiteStatement.bindBlob(6, anh);
        sqLiteStatement.bindString(7, simpleDateFormat.format(ngayNhap));
        sqLiteStatement.bindLong(8, giaTien);
        sqLiteStatement.bindString(9, mapet);
        sqLiteStatement.execute();
    }
     public int LayDoanhThuTheoPet(String loai, String thang){
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        int tongTien = 0;
        String select = "SELECT SUM(giatien) FROM pet "+
                "INNER JOIN hoadonct ON pet.mapet = hoadonct.mapet "+
                "INNER JOIN hoadon ON hoadon.mahoadon = hoadonct.mahd WHERE tenloai = ? AND strftime('%m',hoadon.ngaymua) = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(select,new String[]{loai, thang});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            tongTien = cursor.getInt(0);
        }
        cursor.close();
        return tongTien;
     }
}
