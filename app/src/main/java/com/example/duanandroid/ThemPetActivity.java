package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;


public class ThemPetActivity extends AppCompatActivity {
    Spinner spLoaiPet, spGioiTinh;
    EditText edtMaPet, edtTenPet, edtTuoi, edtMota, edtNgayNhap, edtGiatien;
    ImageView img, imgNgay;
    String loai, gioiTinh;
    TextInputLayout tilMa, tilGiong, tilTuoi, tilNgay, tilGia, tilMoTa;
    Sqlite sqlite;
    PetSql petSql;
    List<Pet> pets;
    int tienPet = 0;

    //www
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_pet);
        setTitle("Thêm Pet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilMa = findViewById(R.id.tilMaPetThem);
        tilGiong = findViewById(R.id.tilGiongPetThem);
        tilTuoi = findViewById(R.id.tilTuoiPetThem);
        tilNgay = findViewById(R.id.tilNgayNhapThem);
        tilGia = findViewById(R.id.tilGiaTienThem);
        tilMoTa = findViewById(R.id.tilMoTa);
        edtNgayNhap = findViewById(R.id.edtNgayNhapThem);
        edtNgayNhap.setEnabled(false);
        spGioiTinh = findViewById(R.id.spGioiTinh);
        edtMaPet = findViewById(R.id.edtMaPetThem);
        edtTenPet = findViewById(R.id.edtTenPetThem);
        edtTuoi = findViewById(R.id.edtTuoiThem);
        edtMota = findViewById(R.id.edtMoTaThem);
        edtNgayNhap = findViewById(R.id.edtNgayNhapThem);
        edtGiatien = findViewById(R.id.edtGiaTienThem);
        img = findViewById(R.id.imgThem);
        imgNgay = findViewById(R.id.imgThemNgayNhapPet);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        edtGiatien.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    edtGiatien.setText("");
                }
                if (hasFocus == false) {
                    if (!edtGiatien.getText().toString().isEmpty()) {
                        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
                        tienPet = Integer.parseInt(edtGiatien.getText().toString());
                        String tien = formatter.format(Integer.parseInt(edtGiatien.getText().toString())) + " VNĐ";
                        edtGiatien.setText(tien);
                    }
                }
            }
        });
        sqlite = new Sqlite(ThemPetActivity.this);
        petSql = new PetSql(sqlite);
        try {
            pets = petSql.LayAllPet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> listLoaiPet = new ArrayList<>();
        listLoaiPet.add("Chó");
        listLoaiPet.add("Mèo");
        spLoaiPet = findViewById(R.id.spLoaiPet);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listLoaiPet);
        spLoaiPet.setAdapter(arrayAdapter);
        spLoaiPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = listLoaiPet.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> gioiTinhList = new ArrayList<>();
        gioiTinhList.add("Đực");
        gioiTinhList.add("Cái");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gioiTinhList);
        spGioiTinh.setAdapter(arrayAdapter1);
        spGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gioiTinh = gioiTinhList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        imgNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemPetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        Calendar calendar1 = new GregorianCalendar(year, month, dayOfMonth);
                        edtNgayNhap.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    public void ThemPet(View view) throws ParseException {
        validate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArray);
        byte[] anh = byteArray.toByteArray();

        String maPet = edtMaPet.getText().toString();
        String tenPet = edtTenPet.getText().toString();
        String tuoi = edtTuoi.getText().toString();
        String moTa = edtMota.getText().toString();
        String date = edtNgayNhap.getText().toString();


        if (validate() > 0) {
            petSql.ThemVatNuoi(maPet, tenPet, loai, gioiTinh, tuoi, moTa, anh, simpleDateFormat.parse(date), tienPet, "Chưa bán");
            Toast.makeText(ThemPetActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ThemPetActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public void HuyPet(View view) {
        finish();
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ThemPetActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                img.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public int validate() {
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        int check = 1;
        boolean checkMaVn = true;
        ArrayList<Pet> arrPet = new ArrayList<>();
        try {
            arrPet = petSql.LayAllPet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (tilMa.getEditText().getText().toString().isEmpty()) {
            tilMa.setError("Không được để trống mã");
            tilMa.setErrorEnabled(true);
            check = -1;
        } else if (tilMa.getEditText().getText().toString().length() < 6) {
            tilMa.setError("Nhập mã ít nhất 6 kí tự");
            tilMa.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilMa.getEditText().getText().toString()).find()) {
            tilMa.setError("Không sử dụng kí tự đặc biệt");
            tilMa.setErrorEnabled(true);
            check = -1;
        } else if (!tilMa.getEditText().getText().toString().substring(0, 3).trim().equals("PET")) {
            tilMa.setError("Mã pet sai đinh dạng! (Ví dụ: PET001)");
            tilMa.setErrorEnabled(true);
            check = -1;
        } else {
            for (int i = 0; i < arrPet.size(); i++) {
                if (edtMaPet.getText().toString().equalsIgnoreCase(arrPet.get(i).getMaPet())) {
                    checkMaVn = false;
                }
            }
            if (checkMaVn == false) {
                tilMa.setError("Mã vật nuôi đã tồn tại");
                tilMa.setErrorEnabled(true);
                check = -1;
            } else {
                tilMa.setErrorEnabled(false);
            }
        }

        if (tilGiong.getEditText().getText().toString().isEmpty()) {
            tilGiong.setError("Không được để trống giống");
            tilGiong.setErrorEnabled(true);
            check = -1;
        } else if (tilGiong.getEditText().getText().toString().length() < 4) {
            tilGiong.setError("Nhập giống pet ít nhất 4 kí tự");
            tilGiong.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilGiong.getEditText().getText().toString()).find()) {
            tilGiong.setError("Không sử dụng kí tự đặc biệt");
            tilGiong.setErrorEnabled(true);
            check = -1;
        } else {
            tilGiong.setErrorEnabled(false);
        }

        if (tilTuoi.getEditText().getText().toString().isEmpty()) {
            tilTuoi.setError("Không được để trống tuổi");
            tilTuoi.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilTuoi.getEditText().getText().toString()).find()) {
            tilTuoi.setError("Không sử dụng kí tự đặc biệt");
            tilTuoi.setErrorEnabled(true);
            check = -1;
        } else {
            tilTuoi.setErrorEnabled(false);
        }

        if (tilNgay.getEditText().getText().toString().isEmpty()) {
            tilNgay.setError("Không được để trống ngày nhập");
            tilNgay.setErrorEnabled(true);
            check = -1;
        } else {
            tilNgay.setErrorEnabled(false);
        }

        if (tilGia.getEditText().getText().toString().isEmpty()) {
            tilGia.setError("Không để trống giá tiền");
            tilGia.setErrorEnabled(true);
            check = -1;
        } else {
            tilGia.setErrorEnabled(false);
        }

        if (tilMoTa.getEditText().getText().toString().isEmpty()) {
            tilMoTa.setError("Không được để trống mô tả");
            tilMoTa.setErrorEnabled(true);
            check = -1;
        } else if (tilMoTa.getEditText().getText().toString().length() > 50) {
            tilMoTa.setError("Nhập tối đa 50 kí tự");
            tilMoTa.setErrorEnabled(true);
            check = -1;
        } else if (special.matcher(tilMoTa.getEditText().getText().toString()).find()) {
            tilMoTa.setError("Không sử dụng kí tự đặc biệt");
            tilMoTa.setErrorEnabled(true);
            check = -1;
        } else {
            tilMoTa.setErrorEnabled(false);
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