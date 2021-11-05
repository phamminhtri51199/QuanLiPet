package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanandroid.Adapter.RecyclerViewNhanVien;
import com.example.duanandroid.ModelClass.NhanVien;
import com.example.duanandroid.Sql.NhanVienSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.ui.QuanLiNhanVien.QuanLiNhanVienFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ThemNhanVienActivity extends AppCompatActivity {
    EditText edtTaiKhoan, edtHoTen, edtMatKhau, edtSdt, edtQuequan;
    Sqlite sqlite;
    TextInputLayout tilTaiKhoan, tilHoTen, tilMatKhau, tilSdt, tilQueQuan;
    List<NhanVien> nhanVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        setTitle("Thêm nhân viên");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoanDangKi);
        edtHoTen = findViewById(R.id.edtHoTenDangKi);
        edtMatKhau = findViewById(R.id.edtMatKhauDangKi);
        edtSdt = findViewById(R.id.edtSoDienThoaiDangKi);
        edtQuequan = findViewById(R.id.edtQueQuan);

        tilTaiKhoan = findViewById(R.id.tilTaiKHoanDangKi);
        tilHoTen = findViewById(R.id.tilHoTenDangKi);
        tilMatKhau = findViewById(R.id.tilMatKhauDangKi);
        tilSdt = findViewById(R.id.tilSoDienThoaiDangKi);
        tilQueQuan = findViewById(R.id.tilQueQuanDangKi);
        sqlite = new Sqlite(ThemNhanVienActivity.this);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void DangKi(View view) {
        NhanVienSql nhanVienSql = new NhanVienSql(sqlite);
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTenTaiKhoan(tilTaiKhoan.getEditText().getText().toString());
        nhanVien.setTenNhanVien(tilHoTen.getEditText().getText().toString());
        nhanVien.setMatKhau(tilMatKhau.getEditText().getText().toString());
        nhanVien.setSdt(tilSdt.getEditText().getText().toString());
        nhanVien.setQueQuan(tilQueQuan.getEditText().getText().toString());
        nhanVien.setTrangThai("active");
        nhanVienList = nhanVienSql.getAllNhanVien();
        nhanVienList.add(0, nhanVien);

        if (validate() > 0) {
            if (nhanVienSql.addNhanVien(nhanVien) > 0) {
                Toast.makeText(ThemNhanVienActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ThemNhanVienActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                tilTaiKhoan.setError("Tài khoản đã tồn tại");
                tilTaiKhoan.setErrorEnabled(true);
            }
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
                for (int i = 0; i < arrNhanVien.size(); i++) {
                    if (tilTaiKhoan.getEditText().getText().toString().equalsIgnoreCase(arrNhanVien.get(i).getTenTaiKhoan())) {
                        checkTk = false;
                    }
                }
                if (checkTk == false) {
                    tilTaiKhoan.setError("Tài khoản đã tồn tại");
                    tilTaiKhoan.setErrorEnabled(true);
                    check = -1;
                } else {
                    tilTaiKhoan.setErrorEnabled(false);
                }
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