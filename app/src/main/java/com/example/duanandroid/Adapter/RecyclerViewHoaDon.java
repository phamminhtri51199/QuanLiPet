package com.example.duanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.ModelClass.HoaDon;
import com.example.duanandroid.ModelClass.HoaDonChiTiet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.HoaDonSql;
import com.example.duanandroid.Sql.Sqlite;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHoaDon extends RecyclerView.Adapter<RecyclerViewHoaDon.HoaDonViewHolder> {
    List<HoaDon> hoaDonList;
    Sqlite sqlite;
    ItemClickListener itemClickListener;

    public RecyclerViewHoaDon(List<HoaDon> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    public void onClickHoaDon(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoadon_layout, null);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvTongTien.setText("Tổng tiền: " + formatter.format(hoaDonSql.LayTongTien(hoaDonList.get(position).getMaHoaDon())) + " VNĐ");
        holder.tvMaHoaDon.setText("Mã hóa đơn: " + hoaDonList.get(position).getMaHoaDon());
        holder.tvKhachHang.setText("Mã khách hàng: " + hoaDonList.get(position).getMaKh());
        holder.tvNhanVien.setText("Tên nhân viên: " + hoaDonList.get(position).getTenTk());
        holder.tvNgayMua.setText("Ngày mua: " + simpleDateFormat.format(hoaDonList.get(position).getNgayMua()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(holder.view, position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (hoaDonList != null) {
            return hoaDonList.size();
        }
        return 0;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHoaDon, tvTongTien, tvNhanVien, tvKhachHang, tvNgayMua;
        View view;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            sqlite = new Sqlite(itemView.getContext());
            tvKhachHang = itemView.findViewById(R.id.tvKhachHang);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvTongTien = itemView.findViewById(R.id.tvTongTienHD);
            tvNhanVien = itemView.findViewById(R.id.tvNhanVien);
            tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
        }
    }
}
