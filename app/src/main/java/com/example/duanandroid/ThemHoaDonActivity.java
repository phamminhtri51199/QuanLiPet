package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duanandroid.ModelClass.HoaDon;
import com.example.duanandroid.Sql.HoaDonSql;
import com.example.duanandroid.Sql.KhachHangSql;
import com.example.duanandroid.Sql.Sqlite;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class ThemHoaDonActivity extends AppCompatActivity {
    ImageView imgThemNgayMua;
    EditText edNgayMua, edtTentk, edtMaKh;
    TextInputLayout tilTenThuNgan, tilMaHoaDon, tilMaKhachHang, tilNgayMua;
    Sqlite sqlite;
    String maVatNuoi, tentk, makh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm hóa đơn");
        setContentView(R.layout.activity_them_hoa_don);
        imgThemNgayMua = findViewById(R.id.imgThemNgayMua);
        edNgayMua = findViewById(R.id.edNgayMua);
        edtTentk = findViewById(R.id.edtTenTk);
        edtMaKh = findViewById(R.id.edtMaKh);
        tilTenThuNgan = findViewById(R.id.tilTenThuNgan);
        tilMaHoaDon = findViewById(R.id.tilMaHoaDon);
        tilMaKhachHang = findViewById(R.id.tilMaKhachHangHD);
        tilNgayMua = findViewById(R.id.tilNgayMua);
        maVatNuoi = getIntent().getExtras().getString("MAVATNUOI");
        edNgayMua.setEnabled(false);
        edtTentk.setEnabled(false);
        edtMaKh.setEnabled(false);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        imgThemNgayMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemHoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        Calendar calendar1 = new GregorianCalendar(year, month, dayOfMonth);
                        edNgayMua.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        sqlite = new Sqlite(ThemHoaDonActivity.this);
        tentk = getIntent().getExtras().getString("TAIKHOAN");
        makh = getIntent().getExtras().getString("MAKH");
        tilTenThuNgan.getEditText().setText(tentk);
        tilMaKhachHang.getEditText().setText(makh);
    }

    public void ThemHoaDonChiTiet(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(tilMaHoaDon.getEditText().getText().toString());
        hoaDon.setTenTk(tilTenThuNgan.getEditText().getText().toString());
        hoaDon.setMaKh(tilMaKhachHang.getEditText().getText().toString());
        hoaDon.setTongTien(0);
        try {
            hoaDon.setNgayMua(simpleDateFormat.parse(tilNgayMua.getEditText().getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (validate() > 0) {
            if (hoaDonSql.ThemHoaDon(hoaDon) > 0) {
                Intent intent = new Intent(ThemHoaDonActivity.this, ThemHoaDonChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("MAVATNUOI", maVatNuoi);
                bundle.putString("MAHOADON", hoaDon.getMaHoaDon());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    public int validate() {
        Pattern special = Pattern.compile("[!@#$%&*^()_+=|<>?{}\\[\\]~-]");
        int check = 1;
        Boolean checkMaHd = true;
        HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
        ArrayList<HoaDon> arrHd = new ArrayList<>();
        try {
            arrHd = hoaDonSql.LayAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tilMaKhachHang.getEditText().getText().toString().isEmpty()) {
            tilMaKhachHang.setError("Không được để trống mã khách hàng");
            tilMaKhachHang.setErrorEnabled(true);
            check = -1;
        } else {
            tilMaKhachHang.setErrorEnabled(false);
        }

        if (tilTenThuNgan.getEditText().getText().toString().isEmpty()) {
            tilTenThuNgan.setError("Không được để trống tên thu ngân");
            tilTenThuNgan.setErrorEnabled(true);
            check = -1;
        } else {
            tilTenThuNgan.setErrorEnabled(false);
        }

        if (tilMaHoaDon.getEditText().getText().toString().isEmpty()) {
            tilMaHoaDon.setError("Không được để trống mã hóa đơn");
            tilMaHoaDon.setErrorEnabled(true);
            check = -1;
        } else if (tilMaHoaDon.getEditText().getText().toString().length() < 5) {
            tilMaHoaDon.setError("Mã hóa đơn ít nhất 5 kí tự");
            tilMaHoaDon.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilMaHoaDon.getEditText().getText().toString()).find()) {
            tilMaHoaDon.setError("Mã hóa đơn không chứa kí tự đặc biệt");
            tilMaHoaDon.setErrorEnabled(true);
            check = -1;
        } else if (!tilMaHoaDon.getEditText().getText().toString().substring(0, 2).trim().equals("HD")) {
            tilMaHoaDon.setError("Mã hóa đơn sai định dạng (Ví dụ: HD001)");
            tilMaHoaDon.setErrorEnabled(true);
            check = -1;
        } else {
            for (int i = 0; i < arrHd.size(); i++) {
                if (tilMaHoaDon.getEditText().getText().toString().equalsIgnoreCase(arrHd.get(i).getMaHoaDon())) {
                    checkMaHd = false;
                }
            }
            if (checkMaHd == false) {
                tilMaHoaDon.setError("Mã hóa đơn đã tồn tại");
                tilMaHoaDon.setErrorEnabled(true);
                check = -1;
            } else {
                tilMaHoaDon.setErrorEnabled(false);
            }
        }

        if (tilNgayMua.getEditText().getText().toString().isEmpty()) {
            tilNgayMua.setError("Không được để trống ngày mua");
            tilNgayMua.setErrorEnabled(true);
            check = -1;
        } else {
            tilNgayMua.setErrorEnabled(false);
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

    public void huyHoaDon(View view) {
        KhachHangSql khachHangSql = new KhachHangSql(sqlite);
        khachHangSql.XoaKhachHang(makh);
        finish();
    }
}