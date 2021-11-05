package com.example.duanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanandroid.Adapter.RecyclerViewPet;
import com.example.duanandroid.Adapter.RecyclerViewThanhToan;
import com.example.duanandroid.ModelClass.HoaDon;
import com.example.duanandroid.ModelClass.HoaDonChiTiet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.Sql.HoaDonCtSql;
import com.example.duanandroid.Sql.HoaDonSql;
import com.example.duanandroid.Sql.KhachHangSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ThemHoaDonChiTietActivity extends AppCompatActivity {

    RecyclerView rvThanhToan;
    Sqlite sqlite;
    List<HoaDonChiTiet> arrHdct;
    TextView tvTongTien;
    ArrayList<String> arrMaVnSelected = new ArrayList<>();
    String mahoadon = "";
    int checkThanhToan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm hóa đơn chi tiết");
        setContentView(R.layout.activity_them_hoa_don_chi_tiet);
        tvTongTien = findViewById(R.id.tvTongTien);
        rvThanhToan = findViewById(R.id.rvThanhToan);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        String mavatnuoi = getIntent().getExtras().getString("MAVATNUOI");
        mahoadon = getIntent().getExtras().getString("MAHOADON");
        sqlite = new Sqlite(ThemHoaDonChiTietActivity.this);
        HoaDonChiTiet hdct = new HoaDonChiTiet("", mahoadon, mavatnuoi);
        HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
        PetSql petSql = new PetSql(sqlite);
        hoaDonCtSql.ThemHdct(hdct);
        arrHdct = new ArrayList<>();
        arrHdct.add(hdct);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        try {
            tvTongTien.setText("Tổng tiền: " + decimalFormat.format(petSql.LayPetTheoMa(hdct.getMaPet()).getGiaTien())+" VNĐ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrMaVnSelected.add(mavatnuoi);
        RecyclerViewThanhToan recyclerViewThanhToan = new RecyclerViewThanhToan(arrHdct);
        rvThanhToan.setAdapter(recyclerViewThanhToan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemHoaDonChiTietActivity.this, RecyclerView.VERTICAL, false);
        rvThanhToan.setLayoutManager(linearLayoutManager);
    }

    public void ThanhToan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThemHoaDonChiTietActivity.this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn mua thêm không??");

        AlertDialog alertDialog = builder.create();
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PetSql petSql = new PetSql(sqlite);
                int tongtien = 0;
                for (int i = 0; i < arrHdct.size(); i++) {
                    petSql.UpdateTrangThai(arrHdct.get(i).getMaPet());
                }
                HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
                HoaDon hoaDon = new HoaDon();
                String taikhoan = "";
                try {
                    hoaDon = hoaDonSql.LayHoaDon(mahoadon).get(0);
                    taikhoan = hoaDon.getTenTk();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < arrHdct.size(); i++) {
                    try {
                        Pet pet = petSql.LayPetTheoMa(arrHdct.get(i).getMaPet());
                        tongtien += pet.getGiaTien();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                hoaDonSql.UpdateTongTien(tongtien, mahoadon);
                Intent intent = new Intent(ThemHoaDonChiTietActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAIKHOAN", taikhoan);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(ThemHoaDonChiTietActivity.this, "Bạn đã thanh toàn thành công", Toast.LENGTH_SHORT).show();
                checkThanhToan = 1;
            }
        });
        builder.create();
        builder.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void muaThem(View view) {
        openDialog(Gravity.CENTER);

    }

    private void openDialog(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.listview_hdct_layout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnHuy = dialog.findViewById(R.id.btnCancelHdct);
        RecyclerView recyclerView = dialog.findViewById(R.id.rvHdctDialog);
        PetSql petSql = new PetSql(sqlite);
        ArrayList<String> arrMaVatNuoi = new ArrayList<>();
        ArrayList<String> arrMaVnUnselected = new ArrayList<>();
        int check = 0;
        try {
            arrMaVatNuoi = petSql.LayAllMaPet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Lấy tất cả mã pet chưa được chọn
        for (int i = 0; i < arrMaVatNuoi.size(); i++) {
            for (int j = 0; j < arrMaVnSelected.size(); j++) {
                if (arrMaVatNuoi.get(i).equalsIgnoreCase(arrMaVnSelected.get(j))) {
                    check++;
                }
            }
            if (check == 0) {
                arrMaVnUnselected.add(arrMaVatNuoi.get(i));
            }
            check = 0;
        }

        ArrayList<Pet> arrVnUnselected = new ArrayList<>();
        ArrayList<Pet> arrThemPet = new ArrayList<>();
        //Lấy tất cả pet chưa được chọn
        for (int i = 0; i < arrMaVnUnselected.size(); i++) {
            Pet pet = new Pet();
            try {
                pet = petSql.LayPetTheoMa(arrMaVnUnselected.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrVnUnselected.add(pet);
        }
        //Lấy tất cả pet ở trạng thái chưa bán
        for (int i = 0; i < arrVnUnselected.size(); i++) {
            if (arrVnUnselected.get(i).getTrangThai().equalsIgnoreCase("Chưa bán")) {
                arrThemPet.add(arrVnUnselected.get(i));
            }
        }

        if (arrThemPet.size() != 0) {
            //Hiện dialog pet chưa bán và chưa chọn
            RecyclerViewPet recyclerHdct = new RecyclerViewPet(arrThemPet, "thanhtoan");
            recyclerView.setAdapter(recyclerHdct);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemHoaDonChiTietActivity.this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerHdct.onClick(new RecyclerViewPet.OnClickItem() {
                @Override
                public void callBackClick(int position) {
                    int tongtien = 0;
                    arrMaVnSelected.add(arrThemPet.get(position).getMaPet());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet("", mahoadon, arrThemPet.get(position).getMaPet());
                    HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
                    hoaDonCtSql.ThemHdct(hoaDonChiTiet);
                    arrHdct.add(hoaDonChiTiet);
                    for (int i = 0; i < arrHdct.size(); i++) {
                        try {
                            Pet pet = petSql.LayPetTheoMa(arrHdct.get(i).getMaPet());
                            tongtien += pet.getGiaTien();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
                    String tien = decimalFormat.format(tongtien) + " VNĐ";
                    tvTongTien.setText("Tổng tiền: " + tien);
                    //reset lại recycle view
                    RecyclerViewThanhToan recyclerViewThanhToan = new RecyclerViewThanhToan(arrHdct);
                    rvThanhToan.setAdapter(recyclerViewThanhToan);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemHoaDonChiTietActivity.this, RecyclerView.VERTICAL, false);
                    rvThanhToan.setLayoutManager(linearLayoutManager);
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "Kho không còn hàng", Toast.LENGTH_SHORT).show();
        }

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Huy(View view) {
        if (checkThanhToan == 0) {
            HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
            ArrayList<HoaDonChiTiet> arrHdct = hoaDonCtSql.LayAllHdctTheoMaHd(mahoadon);
            for (int i = 0; i < arrHdct.size(); i++) {
                hoaDonCtSql.XoaHdct(arrHdct.get(i).getMaHdct());
            }
            HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
            HoaDon hoaDon = new HoaDon();
            String taikhoan = "";
            try {
                hoaDon = hoaDonSql.LayHoaDon(mahoadon).get(0);
                taikhoan = hoaDon.getTenTk();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hoaDonSql.XoaHoaDon(mahoadon);
            KhachHangSql khachHangSql = new KhachHangSql(sqlite);
            khachHangSql.XoaKhachHang(hoaDon.getMaKh());
            Intent intent = new Intent(ThemHoaDonChiTietActivity.this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("TAIKHOAN", taikhoan);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
            HoaDon hoaDon = new HoaDon();
            String taikhoan = "";
            try {
                hoaDon = hoaDonSql.LayHoaDon(mahoadon).get(0);
                taikhoan = hoaDon.getTenTk();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(ThemHoaDonChiTietActivity.this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("TAIKHOAN", taikhoan);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}