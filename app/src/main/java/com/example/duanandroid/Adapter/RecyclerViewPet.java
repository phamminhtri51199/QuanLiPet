package com.example.duanandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.ThemKhachHangActivity;
import com.ramotion.foldingcell.FoldingCell;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPet extends RecyclerView.Adapter<RecyclerViewPet.ChoViewHolder> implements Filterable {

    List<Pet> petList;
    List<Pet> petListOld;
    String taiKhoan;
    OnClickItem onClickItem;

    public RecyclerViewPet(List<Pet> arrPet, String taiKhoan) {
        this.petList = arrPet;
        this.petListOld = arrPet;
        this.taiKhoan = taiKhoan;
    }

    @NonNull
    @Override
    public ChoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_layout, null);
        return new ChoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChoViewHolder holder, int i) {
        Pet pet = petList.get(i);

        if (pet == null) {
            return;
        } else {
            byte[] anh = petList.get(i).getAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //thông tin trên recyclerView
            holder.imgPet.setImageBitmap(bitmap);
            holder.tvMaPet.setText(pet.getMaPet());
            holder.tvTenPet.setText("Giống: " + pet.getTenPet());
            holder.tvGioiTinh.setText("Giới tính: " + pet.getGioiTinh());

            //thông tin All
            DecimalFormat formatter = new DecimalFormat("###,###,###,###");
            holder.imageView.setImageBitmap(bitmap);
            holder.tvMa.setText(petList.get(i).getMaPet());
            holder.tvTen.setText("Giống: " + petList.get(i).getTenPet());
            holder.tvTuoi.setText("Tuổi: " + petList.get(i).getTuoi() + " tháng");
            holder.tvGioiTinh1.setText("Giới tính: " + petList.get(i).getGioiTinh());
            holder.tvNgay.setText("Ngày nhập: " + simpleDateFormat.format(petList.get(i).getNgayNhap()));
            holder.tvGia.setText("Giá: " + formatter.format(petList.get(i).getGiaTien()) + " VNĐ");
            holder.tvMoTa.setText("Mô tả: " + petList.get(i).getMoTa());
        }

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taiKhoan.equalsIgnoreCase("thanhtoan") == false) {
                    onClickItem.callBackClick(i);
                }
            }
        });
        if (taiKhoan.equalsIgnoreCase("Chi tiết hóa đơn")) {
            holder.imgGioHang.setVisibility(View.INVISIBLE);
        } else {
            holder.imgGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (taiKhoan.equalsIgnoreCase("thanhtoan")) {
                        onClickItem.callBackClick(i);
                    } else {
                        Intent intent = new Intent(v.getContext(), ThemKhachHangActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("TAIKHOAN", taiKhoan);
                        bundle.putString("MAVATNUOI", pet.getMaPet());
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (petList != null) {
            return petList.size();
        }
        return 0;
    }

    public class ChoViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imgPet;
        TextView tvMaPet, tvTenPet, tvGioiTinh;
        ImageView imgGioHang;
        FoldingCell foldingCell;
        TextView tvMa, tvTen, tvTuoi, tvGioiTinh1, tvNgay, tvMoTa, tvGia;
        ImageView imageView;

        public ChoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imgPet = itemView.findViewById(R.id.imgPet);
            tvMaPet = itemView.findViewById(R.id.tvMaPet);
            tvTenPet = itemView.findViewById(R.id.tvTenPet);
            imgGioHang = itemView.findViewById(R.id.imgGioHang);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinhPet);
            foldingCell = itemView.findViewById(R.id.folding_cell);

            tvMa = itemView.findViewById(R.id.tvMaPetThongTin);
            tvTen = itemView.findViewById(R.id.tvTenPetThongTin);
            tvTuoi = itemView.findViewById(R.id.tvTuoiPetThongTin);
            tvGioiTinh1 = itemView.findViewById(R.id.tvGioiTinhPetThongTin);
            tvNgay = itemView.findViewById(R.id.tvNgayNhapPetThongTin);
            tvGia = itemView.findViewById(R.id.tvGiaTienThongTin);
            tvMoTa = itemView.findViewById(R.id.tvMoTaPetThongTin);
            imageView = itemView.findViewById(R.id.imgPetThongTin);
        }
    }


    public void onClick(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        void callBackClick(int position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()) {
                    petList = petListOld;
                } else {
                    ArrayList<Pet> list = new ArrayList<>();
                    for (Pet pet : petListOld) {
                        if (pet.getMaPet().toLowerCase().contains(search.toLowerCase())) {
                            list.add(pet);
                        }
                    }
                    petList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = petList;
                filterResults.count = petList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                petList = (ArrayList<Pet>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
