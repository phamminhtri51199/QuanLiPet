package com.example.duanandroid.ui.QuanLiNhanVien;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duanandroid.Adapter.ItemClickListener;
import com.example.duanandroid.Adapter.RecyclerViewNhanVien;
import com.example.duanandroid.ModelClass.NhanVien;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.NhanVienSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.ThemNhanVienActivity;
import com.example.duanandroid.UpdateNhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuanLiNhanVienFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FloatingActionButton fabThemNhanVien;
    public RecyclerView recyclerView;
    List<NhanVien> nhanVienList;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerViewNhanVien recyclerViewNhanVien;

    public QuanLiNhanVienFragment() {
    }

    public static QuanLiNhanVienFragment newInstance(String param1, String param2) {
        QuanLiNhanVienFragment fragment = new QuanLiNhanVienFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_li_nhan_vien, container, false);

        fabThemNhanVien = view.findViewById(R.id.fabThemNhanVien);
        searchView = view.findViewById(R.id.svNhanVien);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout1);

        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.color_toolbar));

        fabThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemNhanVienActivity.class));

            }
        });

        recyclerView = view.findViewById(R.id.rvNhanVien);

        Sqlite sqlite = new Sqlite(getContext());
        NhanVienSql nhanVienSql = new NhanVienSql(sqlite);
        nhanVienList = nhanVienSql.getAllNhanVien();

        recyclerViewNhanVien = new RecyclerViewNhanVien(nhanVienList);
        recyclerView.setAdapter(recyclerViewNhanVien);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewNhanVien.setItemLongClickListener(new ItemClickListener() {
            @Override
            public void onLongClick(View view, int position, boolean isLongClick) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                builder.setTitle("Đổi trạng thái");
                builder.setPositiveButton("Đổi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(nhanVienList.get(position).getTrangThai().equalsIgnoreCase("inactive")){
                            nhanVienSql.UpdateNvActive(nhanVienList.get(position).getTenTaiKhoan());
                        }
                        if(nhanVienList.get(position).getTrangThai().equalsIgnoreCase("active")){
                            nhanVienSql.UpdateNvInactive(nhanVienList.get(position).getTenTaiKhoan());
                        }
                        nhanVienList.removeAll(nhanVienList);
                        nhanVienList.addAll(nhanVienSql.getAllNhanVien());
                        recyclerViewNhanVien.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(container.getContext(), UpdateNhanVien.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAIKHOANUPDATE",nhanVienList.get(position).getTenTaiKhoan());
                bundle.putString("HOTENUPDATE",nhanVienList.get(position).getTenNhanVien());
                bundle.putString("MATKHAUUPDATE",nhanVienList.get(position).getMatKhau());
                bundle.putString("SDTUPDATE",nhanVienList.get(position).getSdt());
                bundle.putString("QUEQUANUPDATE",nhanVienList.get(position).getQueQuan());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fabThemNhanVien.hide();
                } else {
                    fabThemNhanVien.show();
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerViewNhanVien.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewNhanVien.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Sqlite sqlite = new Sqlite(getContext());
                NhanVienSql nhanVienSql = new NhanVienSql(sqlite);
                nhanVienList.removeAll(nhanVienList);
                nhanVienList.addAll(nhanVienSql.getAllNhanVien());
                recyclerViewNhanVien.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }
}