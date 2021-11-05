package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.duanandroid.ModelClass.KhachHang;
import com.example.duanandroid.Sql.KhachHangSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.ui.Dog.DogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ThemKhachHangActivity extends AppCompatActivity {
    TextInputLayout tilMaKhachHang, tilTenKhachHang, tilSoDienThoai;
    Sqlite sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm khách hàng");
        setContentView(R.layout.activity_them_khach_hang);
        tilMaKhachHang = findViewById(R.id.tilMaKhachHang);
        tilTenKhachHang = findViewById(R.id.tilTenKhachHang);
        tilSoDienThoai = findViewById(R.id.tilSoDienThoai);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void ThemHoaDon(View view) {
        String maVatNuoi = getIntent().getExtras().getString("MAVATNUOI");
        String taiKhoan = getIntent().getExtras().getString("TAIKHOAN");
        sqlite = new Sqlite(ThemKhachHangActivity.this);
        KhachHangSql khachHangSql = new KhachHangSql(sqlite);
        KhachHang khachHang = new KhachHang();
        khachHang.setMaKh(tilMaKhachHang.getEditText().getText().toString());
        khachHang.setTenKh(tilTenKhachHang.getEditText().getText().toString());
        khachHang.setSdt(tilSoDienThoai.getEditText().getText().toString());

        if (validate() > 0) {
            if (khachHangSql.ThemKhachHang(khachHang) > 0) {
                Toast.makeText(getApplicationContext(), "Thêm thành công",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThemKhachHangActivity.this, ThemHoaDonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAIKHOAN", taiKhoan);
                bundle.putString("MAVATNUOI", maVatNuoi);
                bundle.putString("MAKH", khachHang.getMaKh());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int validate() {
        Pattern special = Pattern.compile("[!@#$%&^*()_+=|<>?{}\\[\\]~-]");
        int check = 1;
        boolean checkMaKh = true;
        Sqlite sqlite = new Sqlite(ThemKhachHangActivity.this);
        KhachHangSql khachHangSql = new KhachHangSql(sqlite);
        ArrayList<KhachHang> arrKhachHang = khachHangSql.LayAllKhachHang();
        for (int i = 0; i < arrKhachHang.size(); i++) {
            if (tilMaKhachHang.getEditText().getText().toString().equalsIgnoreCase(arrKhachHang.get(i).getMaKh())) {
                checkMaKh = false;
            }
        }
        if (tilMaKhachHang.getEditText().getText().toString().isEmpty()) {
            tilMaKhachHang.setError("Không được để trống mã khách hàng");
            tilMaKhachHang.setErrorEnabled(true);
            check = -1;
        } else if (tilMaKhachHang.getEditText().getText().toString().length() < 5) {
            tilMaKhachHang.setError("Mã khách hàng ít nhất 5 kí tự");
            tilMaKhachHang.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilMaKhachHang.getEditText().getText().toString()).find()) {
            tilMaKhachHang.setError("Mã khách hàng không chứa kí tự đặc biệt");
            tilMaKhachHang.setErrorEnabled(true);
            check = -1;
        } else if (!tilMaKhachHang.getEditText().getText().toString().substring(0, 2).trim().equals("KH")) {
            tilMaKhachHang.setError("Mã khách hàng sai định dạng (Ví dụ: KH001)");
            tilMaKhachHang.setErrorEnabled(true);
            check = -1;
        } else {
            if (checkMaKh == false) {
                tilMaKhachHang.setError("Mã khách hàng đã tồn tại");
                tilMaKhachHang.setErrorEnabled(true);
                check = -1;
            } else {
                tilMaKhachHang.setErrorEnabled(false);
            }
        }
        if (tilTenKhachHang.getEditText().getText().toString().isEmpty()) {
            tilTenKhachHang.setError("Không được để trống tên khách hàng");
            tilTenKhachHang.setErrorEnabled(true);
            check = -1;
        } else if (tilTenKhachHang.getEditText().getText().toString().length() < 2) {
            tilTenKhachHang.setError("Tên khách hàng ít nhất 2 kí tự");
            tilTenKhachHang.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilTenKhachHang.getEditText().getText().toString()).find()) {
            tilTenKhachHang.setError("Tên khách hàng không chứa kí tự đặc biệt");
            tilTenKhachHang.setErrorEnabled(true);
            check = -1;
        } else {
            tilTenKhachHang.setErrorEnabled(false);
        }

        if (tilSoDienThoai.getEditText().getText().toString().isEmpty()) {
            tilSoDienThoai.setError("Không được để trống số điện thoại");
            tilSoDienThoai.setErrorEnabled(true);
            check = -1;
        }
        if (tilSoDienThoai.getEditText().getText().toString().length() < 10 || tilSoDienThoai.getEditText().getText().toString().length() > 10) {
            tilSoDienThoai.setError("Sai định dạng số điện thoại");
            tilSoDienThoai.setErrorEnabled(true);
            check = -1;
        } else {
            tilSoDienThoai.setErrorEnabled(false);
        }
        return check;
    }

    public void HuyKhachHang(View view) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}