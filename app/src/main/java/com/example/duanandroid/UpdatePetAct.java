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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.Sql.PetSql;
import com.example.duanandroid.Sql.Sqlite;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

public class UpdatePetAct extends AppCompatActivity {
    Spinner spLoaiPet, spGioiTinh;
    EditText edtMaPet, edtTenPet, edtTuoi, edtMota, edtNgayNhap, edtGiatien;
    ImageView img, imgNgay;
    String loai, gioiTinh;
    TextInputLayout tilMa, tilGiong, tilTuoi, tilNgay, tilGia, tilMoTa;
    Sqlite sqlite;
    int loaiIndex = 0;
    int gioiTinhIndex = 0;
    int tienPet = 0;
    PetSql petSql;
    List<Pet> pets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pet);
        setTitle("Thêm Pet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tilMa = findViewById(R.id.tilMaPetUpdate);
        tilGiong = findViewById(R.id.tilGiongPetUpdate);
        tilTuoi = findViewById(R.id.tilTuoiPetUpdate);
        tilNgay = findViewById(R.id.tilNgayNhapUpdate);
        tilGia = findViewById(R.id.tilGiaTienUpdate);
        tilMoTa = findViewById(R.id.tilMoTaUpdate);
        edtNgayNhap = findViewById(R.id.edtNgayNhapUpdate);
        edtNgayNhap.setEnabled(false);
        spGioiTinh = findViewById(R.id.spGioiTinhUpdate);
        edtMaPet = findViewById(R.id.edtMaPetUpdate);
        edtMaPet.setEnabled(false);
        edtTenPet = findViewById(R.id.edtTenPetUpdate);
        edtTuoi = findViewById(R.id.edtTuoiUpdate);
        edtMota = findViewById(R.id.edtMoTaUpdate);
        edtGiatien = findViewById(R.id.edtGiaTienUpdate);
        img = findViewById(R.id.imgUpdate);
        imgNgay = findViewById(R.id.imgUpdateNgayNhapPet);
        spLoaiPet = findViewById(R.id.spLoaiPetUpdate);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        List<String> listLoaiPet = new ArrayList<>();
        listLoaiPet.add("Chó");
        listLoaiPet.add("Mèo");

        List<String> gioiTinhList = new ArrayList<>();
        gioiTinhList.add("Đực");
        gioiTinhList.add("Cái");

        sqlite = new Sqlite(UpdatePetAct.this);
        petSql = new PetSql(sqlite);
        try {
            pets = petSql.LayAllPet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edtGiatien.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    edtGiatien.setText("");
                }
                if(hasFocus==false){
                    if(!edtGiatien.getText().toString().isEmpty()){
                        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
                        tienPet = Integer.parseInt(edtGiatien.getText().toString());
                        String tien = formatter.format(Integer.parseInt(edtGiatien.getText().toString()))+" VNĐ";
                        edtGiatien.setText(tien);
                    }
                }
            }
        });

        String maPet = getIntent().getExtras().getString("MAPETUPDATE");
        edtMaPet.setText(maPet);
        String loaiPet = getIntent().getExtras().getString("LOAIPETUPDATE");
        String giongPet = getIntent().getExtras().getString("GIONGPETUPDATE");
        String tuoiPet = getIntent().getExtras().getString("TUOIPETUPDATE");
        String gioiTinhpet = getIntent().getExtras().getString("GIOITINHUPDATE");
        String ngayNhap = getIntent().getExtras().getString("NGAYNHAPUPDATE");
        int giaTien = getIntent().getExtras().getInt("GIATIENUPDATE");
        String moTa = getIntent().getExtras().getString("MOTAUPDATE");
        tienPet = giaTien;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        String tien = decimalFormat.format(giaTien)+ " VNĐ";
        tilGiong.getEditText().setText(giongPet);
        tilTuoi.getEditText().setText(tuoiPet);
        edtNgayNhap.setText(ngayNhap);
        edtGiatien.setText(tien);
        edtMota.setText(moTa);

        for(int i =0; i<listLoaiPet.size();i++){
            if(loaiPet.equalsIgnoreCase(listLoaiPet.get(i))){
                loaiIndex = i;
            }
        }
        for(int i =0; i<gioiTinhList.size();i++){
            if(gioiTinhpet.equalsIgnoreCase(gioiTinhList.get(i))){
                gioiTinhIndex = i;
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listLoaiPet);
        spLoaiPet.setAdapter(arrayAdapter);
        spLoaiPet.setSelection(loaiIndex);
        spLoaiPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = listLoaiPet.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gioiTinhList);
        spGioiTinh.setAdapter(arrayAdapter1);
        spGioiTinh.setSelection(gioiTinhIndex);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePetAct.this, new DatePickerDialog.OnDateSetListener() {
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

    public void HuyPet(View view) {
        finish();
    }

    public void Update(View view) throws ParseException {
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
        String giaTien = edtGiatien.getText().toString();

        if (validate() > 0) {
            petSql.UpdatePet(maPet, tenPet, loai, gioiTinh, tuoi, moTa, anh, simpleDateFormat.parse(date), tienPet);
            Toast.makeText(UpdatePetAct.this, "Update thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(UpdatePetAct.this, "Update không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(UpdatePetAct.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
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
            tilMa.setErrorEnabled(false);
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