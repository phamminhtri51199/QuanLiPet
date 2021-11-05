package com.example.duanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.ModelClass.NhanVien;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.Sqlite;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewNhanVien extends RecyclerView.Adapter<RecyclerViewNhanVien.NhanVienViewHolder> implements Filterable {
    List<NhanVien> nhanVienList;
    List<NhanVien> nhanVienListOld;
    Sqlite sqlite;
    ItemClickListener itemClickListener;
    public RecyclerViewNhanVien(List<NhanVien> nhanVienList) {
        this.nhanVienList = nhanVienList;
        this.nhanVienListOld = nhanVienList;
    }
    public void setItemLongClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhanvien_layout, null);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int i) {
        if (nhanVienList == null) {
            return;
        } else {
            holder.tvTenTaiKhoan.setText("Tên tài khoản: " + nhanVienList.get(i).getTenTaiKhoan());
            holder.tvTenNhanVien.setText("Tên nhân viên: " + nhanVienList.get(i).getTenNhanVien());
            holder.tvSDT.setText("SĐT: " + nhanVienList.get(i).getSdt());
            if (i % 3 == 1) {
                holder.imageView.setImageResource(R.drawable.man);
            }
            if (i % 3 == 2) {
                holder.imageView.setImageResource(R.drawable.woman);
            }
            if (i % 3 == 0) {
                holder.imageView.setImageResource(R.drawable.programmer);
            }
        }
        if(nhanVienList.get(i).getTrangThai().equalsIgnoreCase("active")){
            holder.tvTrangThai.setText("Active");
            holder.tvTrangThai.setTextColor(holder.view.getResources().getColor(R.color.teal_700));
        }
        if(nhanVienList.get(i).getTrangThai().equalsIgnoreCase("inactive")){
            holder.tvTrangThai.setText("Inactive");
            holder.tvTrangThai.setTextColor(holder.view.getResources().getColor(R.color.red));
        }
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onLongClick(holder.view,i,true);
                return false;
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(holder.view,i,false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (nhanVienList != null) {
            return nhanVienList.size();
        }
        return 0;
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenTaiKhoan, tvTenNhanVien, tvSDT, tvTrangThai;
        ImageView imageView;
        View view;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            tvTenTaiKhoan = itemView.findViewById(R.id.tvTenTaiKhoan);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvSDT = itemView.findViewById(R.id.tvSoDienThoai);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThaiNv);
            imageView = itemView.findViewById(R.id.imgNhanVien);
            sqlite = new Sqlite(itemView.getContext());
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()) {
                    nhanVienList = nhanVienListOld;
                } else {
                    List<NhanVien> list = new ArrayList<>();
                    for (NhanVien nhanVien : nhanVienListOld) {
                        if (nhanVien.getTenTaiKhoan().toLowerCase().contains(search.toLowerCase())) {
                            list.add(nhanVien);
                        }
                    }
                    nhanVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = nhanVienList;
                filterResults.count = nhanVienList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                nhanVienList = (List<NhanVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
