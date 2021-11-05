package com.example.duanandroid.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.ModelClass.HoaDonChiTiet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;

import java.text.ParseException;
import java.util.List;

public class RecyclerViewThanhToan extends RecyclerView.Adapter<RecyclerViewThanhToan.ThanhToanViewHolder> {
    List<HoaDonChiTiet> hoaDonChiTietList;
    Sqlite sqlite;

    public RecyclerViewThanhToan(List<HoaDonChiTiet> hoaDonChiTietList) {
        this.hoaDonChiTietList = hoaDonChiTietList;
    }

    @NonNull
    @Override
    public ThanhToanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qlypet_layout, null);
        return new ThanhToanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhToanViewHolder holder, int i) {
        PetSql petSql = new PetSql(sqlite);
        Pet pet = new Pet();
        try {
            pet = petSql.LayPetTheoMa(hoaDonChiTietList.get(i).getMaPet());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (pet == null) {
            return;
        } else {
            byte[] anh = pet.getAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            holder.imgAvt.setImageBitmap(bitmap);

            holder.tvMaVatNuoiKho.setText("Mã: " + pet.getMaPet());
            holder.tvTenVatNuoiKho.setText("Giống: " + pet.getTenPet());
            holder.tvLoai.setText("Loại: " + pet.getTenLoai());
        }
    }

    @Override
    public int getItemCount() {
        if (hoaDonChiTietList != null) {
            return hoaDonChiTietList.size();
        }
        return 0;
    }

    public class ThanhToanViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvt;
        TextView tvMaVatNuoiKho, tvTenVatNuoiKho, tvLoai;

        public ThanhToanViewHolder(@NonNull View itemView) {
            super(itemView);
            sqlite = new Sqlite(itemView.getContext());
            imgAvt = itemView.findViewById(R.id.imgPetKho);
            tvMaVatNuoiKho = itemView.findViewById(R.id.tvMaPetKho);
            tvTenVatNuoiKho = itemView.findViewById(R.id.tvTenPetKho);
            tvLoai = itemView.findViewById(R.id.tvLoaiPetKho);
        }
    }
}
