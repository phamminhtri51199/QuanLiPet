package com.example.duanandroid.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.ModelClass.HoaDonChiTiet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.HoaDonCtSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;
import com.example.duanandroid.UpdatePetAct;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewQlyPet extends RecyclerView.Adapter<RecyclerViewQlyPet.KhoViewHolder> {
    List<Pet> petList;
    Sqlite sqlite;

    public RecyclerViewQlyPet(List<Pet> petList) {
        this.petList = petList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qlypet_layout, null);
        return new KhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhoViewHolder holder, int i) {
        PetSql petSql = new PetSql(sqlite);
        Pet pet = petList.get(i);
        byte[] anh = petList.get(i).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        holder.imgAvt.setImageBitmap(bitmap);
        holder.tvMaVatNuoiKho.setText(pet.getMaPet());
        holder.tvTenVatNuoiKho.setText("Giống: " + pet.getTenPet());
        holder.tvLoai.setText("Tuổi: " + pet.getTuoi());
        holder.tvTrangThai.setText(pet.getTrangThai());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onLongClick(View view, int position, boolean isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Bạn có muốn xóa vật nuôi mã " + petList.get(position).getMaPet() + " không?");
                    Log.e("1", String.valueOf(position));
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
                            ArrayList<HoaDonChiTiet> arrHdct = hoaDonCtSql.LayAllHdct();

                            int check = 0;
                            for (int i = 0; i < arrHdct.size(); i++) {
                                if (arrHdct.get(i).getMaPet().equalsIgnoreCase(petList.get(position).getMaPet())) {
                                    check++;
                                }
                            }
                            if (check == 0) {
                                petSql.XoaPet(petList.get(position).getMaPet());
                                petList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(holder.view.getContext(), "Pet đang tồn tại trong đơn hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.create();
                    builder.show();
            }

            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                view = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_pet_layout, null);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                builder.setTitle("Thông tin Pet");

                TextView tvMa = view.findViewById(R.id.tvMaPetThongTin);
                TextView tvTen = view.findViewById(R.id.tvTenPetThongTin);
                TextView tvTuoi = view.findViewById(R.id.tvTuoiPetThongTin);
                TextView tvGioiTinh = view.findViewById(R.id.tvGioiTinhPetThongTin);
                TextView tvNgay = view.findViewById(R.id.tvNgayNhapPetThongTin);
                TextView tvGia = view.findViewById(R.id.tvGiaTienThongTin);
                TextView tvMoTa = view.findViewById(R.id.tvMoTaPetThongTin);
                ImageView imageView = view.findViewById(R.id.imgPetThongTin);

                DecimalFormat formatter = new DecimalFormat("###,###,###,###");
                byte[] anh = petList.get(position).getAnh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
                imageView.setImageBitmap(bitmap);

                tvMa.setText("Mã: " + petList.get(position).getMaPet());
                tvTen.setText("Giống: " + petList.get(position).getTenPet());
                tvTuoi.setText("Tuổi: " + petList.get(position).getTuoi() + " tháng");
                tvGioiTinh.setText("Giới tính: " + petList.get(position).getGioiTinh());
                tvNgay.setText("Ngày nhập: " + simpleDateFormat.format(petList.get(position).getNgayNhap()));
                tvGia.setText("Giá: " + formatter.format(petList.get(position).getGiaTien()) + " VNĐ");
                tvMoTa.setText("Mô tả: " + petList.get(position).getMoTa());
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
                        ArrayList<HoaDonChiTiet> arrHdct = hoaDonCtSql.LayAllHdct();

                        int check = 0;
                        for (int i = 0; i < arrHdct.size(); i++) {
                            if (arrHdct.get(i).getMaPet().equalsIgnoreCase(petList.get(position).getMaPet())) {
                                check++;
                            }
                        }
                        if (check == 0) {
                            Intent intent = new Intent(holder.view.getContext(), UpdatePetAct.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("MAPETUPDATE",petList.get(position).getMaPet());
                            bundle.putString("LOAIPETUPDATE",petList.get(position).getTenLoai());
                            bundle.putString("GIONGPETUPDATE",petList.get(position).getTenPet());
                            bundle.putString("TUOIPETUPDATE",petList.get(position).getTuoi());
                            bundle.putString("GIOITINHUPDATE",petList.get(position).getGioiTinh());
                            bundle.putString("NGAYNHAPUPDATE",simpleDateFormat.format(petList.get(position).getNgayNhap()));
                            bundle.putInt("GIATIENUPDATE",petList.get(position).getGiaTien());
                            bundle.putString("MOTAUPDATE",petList.get(position).getMoTa());
                            intent.putExtras(bundle);
                            holder.view.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(holder.view.getContext(), "Pet đang tồn tại trong đơn hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view);
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (petList != null) {
            return petList.size();
        }
        return 0;
    }

    public class KhoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgAvt;
        TextView tvMaVatNuoiKho, tvTenVatNuoiKho, tvLoai, tvTrangThai;
        View view;

        public KhoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            sqlite = new Sqlite(itemView.getContext());
            imgAvt = itemView.findViewById(R.id.imgPetKho);
            tvMaVatNuoiKho = itemView.findViewById(R.id.tvMaPetKho);
            tvTenVatNuoiKho = itemView.findViewById(R.id.tvTenPetKho);
            tvLoai = itemView.findViewById(R.id.tvLoaiPetKho);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThaiKho);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onLongClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
