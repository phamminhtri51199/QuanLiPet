package com.example.duanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanandroid.ModelClass.NhanVien;
import com.example.duanandroid.Sql.NhanVienSql;
import com.example.duanandroid.Sql.Sqlite;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtTaiKhoan, edtMatKhau;
    NhanVienSql nhanVienSql;
    Sqlite sqlite;
    TextInputLayout tilTaiKhoan, tilMatKhau;
    SharedPreferences sharedPreferences;
    CheckBox cbLuuMatKhau;
    Button btnDangNhap;
    ArrayList<NhanVien> arrNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tvTitle);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "Pacifico.ttf"));

        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        tilTaiKhoan = findViewById(R.id.tilTaiKhoan);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        cbLuuMatKhau = findViewById(R.id.cbLuuMatKhau);

        sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        sqlite = new Sqlite(MainActivity.this);
        nhanVienSql = new NhanVienSql(sqlite);
        arrNhanVien = nhanVienSql.getAllNhanVien();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        int k = 0;
        for (int i = 0; i < arrNhanVien.size(); i++) {
            if (arrNhanVien.get(i).getTenTaiKhoan().equalsIgnoreCase("admin")) {
                k++;
            }
        }
        if (k == 0) {
            NhanVien nhanVien = new NhanVien("admin", "admin", "admin", "000", "","active");
            nhanVienSql.addNhanVien(nhanVien);
        }
        getPreferencesData();
    }

    public void login(View view) {
        if (validate() > 0) {
            for (int i = 0; i < arrNhanVien.size(); i++) {
                if (tilTaiKhoan.getEditText().getText().toString().equalsIgnoreCase(arrNhanVien.get(i).getTenTaiKhoan()) && tilMatKhau.getEditText().getText().toString().equalsIgnoreCase(arrNhanVien.get(i).getMatKhau())&&arrNhanVien.get(i).getTrangThai().equalsIgnoreCase("active")) {
                    if (cbLuuMatKhau.isChecked()) {
                        Boolean aBoolean = cbLuuMatKhau.isChecked();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", edtTaiKhoan.getText().toString());
                        editor.putString("pass", edtMatKhau.getText().toString());
                        editor.putBoolean("checkbox", aBoolean);
                        editor.apply();
                    }
                    else {
                        sharedPreferences.edit().clear().apply();
                    }

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TAIKHOAN", edtTaiKhoan.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else if(tilTaiKhoan.getEditText().getText().toString().equalsIgnoreCase(arrNhanVien.get(i).getTenTaiKhoan()) && tilMatKhau.getEditText().getText().toString().equalsIgnoreCase(arrNhanVien.get(i).getMatKhau())&&arrNhanVien.get(i).getTrangThai().equalsIgnoreCase("inactive")){
                    Toast.makeText(this, "Tài khoản hiện không hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public int validate() {
        int check = 1;
        boolean checkTk = true;
        String checkMk="";
        int checkEmptyMk=0;
        NhanVienSql nhanVienSql=new NhanVienSql(sqlite);
        ArrayList<NhanVien> arrNhanVien = nhanVienSql.getAllNhanVien();
        if (tilTaiKhoan.getEditText().getText().toString().isEmpty()) {
            tilTaiKhoan.setError("Không được để trống tài khoản");
            tilTaiKhoan.setErrorEnabled(true);
            check = -1;
        } else {
            for (int i = 0; i < arrNhanVien.size(); i++) {
                if (tilTaiKhoan.getEditText().getText().toString().equals(arrNhanVien.get(i).getTenTaiKhoan())) {
                    checkTk = false;
                    checkMk = nhanVienSql.layMkTheoTk(tilTaiKhoan.getEditText().getText().toString());
                }
            }

            if (checkTk == true) {
                tilTaiKhoan.setError("Tài khoản không tồn tại");
                tilTaiKhoan.setErrorEnabled(true);
                check = -1;
            } else if(tilMatKhau.getEditText().getText().toString().equals(checkMk)==false){
                tilTaiKhoan.setErrorEnabled(false);
                checkEmptyMk=1;
                tilMatKhau.setError("Sai mật khẩu");
                tilMatKhau.setErrorEnabled(true);
                check=-1;
            }
            else
            {
                tilTaiKhoan.setErrorEnabled(false);
            }
        }
        if (tilMatKhau.getEditText().getText().toString().isEmpty()) {
            tilMatKhau.setError("Không được để trống mật khẩu");
            tilMatKhau.setErrorEnabled(true);
            check = -1;
        } else if(checkEmptyMk==1){
            tilMatKhau.setError("Sai mật khẩu");
            tilMatKhau.setErrorEnabled(true);
            check=-1;
        } else {
            tilMatKhau.setErrorEnabled(false);
        }
        return check;
    }

    public void getPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        if (sharedPreferences.contains("username")) {
            String user = sharedPreferences.getString("username", "not found.");
            edtTaiKhoan.setText(user);
        }
        if (sharedPreferences.contains("pass")) {
            String pass = sharedPreferences.getString("pass", "not found.");
            edtMatKhau.setText(pass);
        }
        if (sharedPreferences.contains("checkbox")) {
            Boolean check = sharedPreferences.getBoolean("checkbox", false);
            cbLuuMatKhau.setChecked(check);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}