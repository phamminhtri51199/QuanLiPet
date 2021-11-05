package com.example.duanandroid.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
    Context context;

    public Sqlite(Context context) {
        super(context, "sqlpettestlanthu3.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_nhanvien = "CREATE TABLE nhanvien " +
                "(tentk varchar(50) primary key not null, " +
                "tennv nvarchar(200), " +
                "mk varchar(50), " +
                "sdt varchar(50), " +
                "quequan nvarchar(250),"+
                "trangthai nvarchar(100))";
        db.execSQL(create_nhanvien);
        String creat_pet = "CREATE TABLE pet " +
                "(mapet varchar(50) primary key not null, " +
                "tenpet nvarchar(250) not null, " +
                "tenloai nvarchar(100) not null, " +
                "gioitinh nvarchar(50), " +
                "tuoi nvarchar(50), " +
                "mota nvarchar(255), " +
                "anh blob not null, " +
                "ngaynhap date not null," +
                "giatien int not null," +
                "trangthai nvarchar not null)";
        db.execSQL(creat_pet);
        String create_khachhang = "CREATE TABLE khachhang " +
                "(makh varchar(50) primary key, " +
                "tenkh nvarchar(255) not null, " +
                "sdt varchar(50) not null)";
        db.execSQL(create_khachhang);
        String create_hoadon = "CREATE TABLE hoadon " +
                "(mahoadon varchar(50) primary key, " +
                "tentk varchar(50), " +
                "makh varchar(50), " +
                "ngaymua date, " +
                "tongtien int not null, "+
                "foreign key(tentk) references nhanvien(tentk)," +
                "foreign key(makh) references khachhang(makh))";
        db.execSQL(create_hoadon);
        String create_hoadonct = "CREATE TABLE hoadonct " +
                "(mahdct INTEGER primary key autoincrement, " +
                "mahd varchar(50), " +
                "mapet varchar(50), " +
                "foreign key(mahd) references hoadon(mahoadon), " +
                "foreign key(mapet) references pet(mapet))";
        db.execSQL(create_hoadonct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
