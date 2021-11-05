package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanandroid.ModelClass.NhanVien;
import com.example.duanandroid.Sql.NhanVienSql;
import com.example.duanandroid.Sql.Sqlite;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class UpdateNhanVien extends AppCompatActivity {
    EditText edtTaiKhoan, edtHoTen, edtMatKhau, edtSdt, edtQuequan;
    Sqlite sqlite;
    TextInputLayout tilTaiKhoan, tilHoTen, tilMatKhau, tilSdt, tilQueQuan;
    String taiKhoan, hoTen, matKhau, sdt, queQuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nhan_vien);
        setTitle("Thêm nhân viên");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoanUpdate);
        edtHoTen = findViewById(R.id.edtHoTenUpdate);
        edtMatKhau = findViewById(R.id.edtMatKhauUpdate);
        edtSdt = findViewById(R.id.edtSoDienThoaiUpdate);
        edtQuequan = findViewById(R.id.edtQueQuanUpdate);

        taiKhoan = getIntent().getExtras().getString("TAIKHOANUPDATE");
        hoTen = getIntent().getExtras().getString("HOTENUPDATE");
        matKhau = getIntent().getExtras().getString("MATKHAUUPDATE");
        sdt = getIntent().getExtras().getString("SDTUPDATE");
        queQuan = getIntent().getExtras().getString("QUEQUANUPDATE");

        tilTaiKhoan = findViewById(R.id.tilTaiKHoanUpdate);
        tilHoTen = findViewById(R.id.tilHoTenUpdate);
        tilMatKhau = findViewById(R.id.tilMatKhauUpdate);
        tilSdt = findViewById(R.id.tilSoDienThoaiUpdate);
        tilQueQuan = findViewById(R.id.tilQueQuanUpdate);
        sqlite = new Sqlite(UpdateNhanVien.this);
        edtTaiKhoan.setEnabled(false);

        edtTaiKhoan.setText(taiKhoan);
        edtHoTen.setText(hoTen);
        edtMatKhau.setText(matKhau);
        edtQuequan.setText(queQuan);
        edtSdt.setText(sdt);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void Update(View view) {
        NhanVienSql nhanVienSql = new NhanVienSql(sqlite);
        if (validate() > 0) {
            nhanVienSql.UpdateNhanVien(taiKhoan,edtHoTen.getText().toString(),edtMatKhau.getText().toString(),edtSdt.getText().toString(),edtQuequan.getText().toString());
            Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "Update không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public int validate() {
        Pattern special = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]");
        int check = 1;
        boolean checkTk = true;
        NhanVienSql nhanVienSql = new NhanVienSql(sqlite);
        ArrayList<NhanVien> arrNhanVien = nhanVienSql.getAllNhanVien();
        if (tilTaiKhoan.getEditText().getText().toString().isEmpty()) {
            tilTaiKhoan.setError("Không được để trống tên tài khoản");
            tilTaiKhoan.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilTaiKhoan.getEditText().getText().toString()).find()) {
            tilTaiKhoan.setError("Không sử dụng kí tự đặc biệt");
            tilTaiKhoan.setErrorEnabled(true);
            check = -1;
        } else {
            if (tilTaiKhoan.getEditText().getText().length() > 10) {
                tilTaiKhoan.setError("Tài khoản tối đa 10 kí tự");
                tilTaiKhoan.setErrorEnabled(true);
                check = -1;
            } else {
                tilTaiKhoan.setErrorEnabled(false);
            }
        }

        if (tilHoTen.getEditText().getText().toString().isEmpty()) {
            tilHoTen.setError("Không được để trống họ tên");
            tilHoTen.setErrorEnabled(true);
            check = -1;
        } else if (tilHoTen.getEditText().getText().toString().length() < 2) {
            tilHoTen.setError("Nhập ít nhất 2 kí tự");
            tilHoTen.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilHoTen.getEditText().getText().toString()).find()) {
            tilHoTen.setError("Không sử dụng kí tự đặc biệt");
            tilHoTen.setErrorEnabled(true);
            check = -1;
        } else {
            tilHoTen.setErrorEnabled(false);
        }

        if (tilSdt.getEditText().getText().toString().isEmpty()) {
            tilSdt.setError("Không được để trống số điện thoại");
            tilSdt.setErrorEnabled(true);
            check = -1;
        }
        if (tilSdt.getEditText().getText().toString().length() > 10 || tilSdt.getEditText().getText().toString().length() < 10) {
            tilSdt.setError("Sai định dạng số điện thoại");
            tilSdt.setErrorEnabled(true);
            check = -1;
        } else if (tilSdt.getEditText().getText().toString().length() < 8) {
            tilSdt.setError("Nhập ít nhất 10 số");
            tilSdt.setErrorEnabled(true);
            check = -1;
        } else {
            tilSdt.setErrorEnabled(false);
        }

        if (tilMatKhau.getEditText().getText().toString().isEmpty()) {
            tilMatKhau.setError("Không được để trống mật khẩu");
            tilMatKhau.setErrorEnabled(true);
            check = -1;
        } else if (tilMatKhau.getEditText().getText().toString().length() < 8) {
            tilMatKhau.setError("Nhập ít nhất 8 kí tự");
            tilMatKhau.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilMatKhau.getEditText().getText().toString()).find()) {
            tilMatKhau.setError("Không sử dụng kí tự đặc biệt");
            tilMatKhau.setErrorEnabled(true);
            check = -1;
        } else {
            tilMatKhau.setErrorEnabled(false);
        }

        if (tilQueQuan.getEditText().getText().toString().isEmpty()) {
            tilQueQuan.setError("Không được để trống quê quán");
            tilQueQuan.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilQueQuan.getEditText().getText().toString()).find()) {
            tilQueQuan.setError("Không sử dụng kí tự đặc biệt");
            tilQueQuan.setErrorEnabled(true);
            check = -1;
        } else {
            tilQueQuan.setErrorEnabled(false);
        }

        return check;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}