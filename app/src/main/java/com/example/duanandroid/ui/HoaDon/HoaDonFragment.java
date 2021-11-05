package com.example.duanandroid.ui.HoaDon;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.duanandroid.Adapter.ItemClickListener;
import com.example.duanandroid.Adapter.RecyclerViewHoaDon;
import com.example.duanandroid.Adapter.RecyclerViewPet;
import com.example.duanandroid.ModelClass.HoaDon;
import com.example.duanandroid.ModelClass.HoaDonChiTiet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.HoaDonCtSql;
import com.example.duanandroid.Sql.HoaDonSql;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;

import java.text.ParseException;
import java.util.ArrayList;

public class HoaDonFragment extends Fragment {

    RecyclerView rvHoaDon;
    ArrayList<HoaDon> hoaDonList;
    Sqlite sqlite;
    int position1 = 0;

    public HoaDonFragment() {
        // Required empty public constructor
    }

    public static HoaDonFragment newInstance(String param1, String param2) {
        HoaDonFragment fragment = new HoaDonFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        rvHoaDon = view.findViewById(R.id.rvHoaDon);
        Spinner spnHdThang = view.findViewById(R.id.spnHdThang);
        sqlite = new Sqlite(getActivity());
        HoaDonSql hoaDonSql = new HoaDonSql(sqlite);
        ArrayList<String> arrThang = new ArrayList<>();
        arrThang.add("Tất cả");
        arrThang.add("Tháng 1");
        arrThang.add("Tháng 2");
        arrThang.add("Tháng 3");
        arrThang.add("Tháng 4");
        arrThang.add("Tháng 5");
        arrThang.add("Tháng 6");
        arrThang.add("Tháng 7");
        arrThang.add("Tháng 8");
        arrThang.add("Tháng 9");
        arrThang.add("Tháng 10");
        arrThang.add("Tháng 11");
        arrThang.add("Tháng 12");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrThang);
        spnHdThang.setAdapter(adapter);

        try {
            hoaDonList = hoaDonSql.LayAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RecyclerViewHoaDon recyclerViewHoaDon = new RecyclerViewHoaDon(hoaDonList);
        rvHoaDon.setAdapter(recyclerViewHoaDon);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvHoaDon.setLayoutManager(linearLayoutManager);
        spnHdThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    try {
                        ArrayList<HoaDon> arrHdThang = hoaDonSql.LayAllHoaDon();
                        hoaDonList.removeAll(hoaDonList);
                        hoaDonList.addAll(arrHdThang);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewHoaDon.notifyDataSetChanged();
                }
                if (position > 0 && position < 10) {
                    String thang = "0" + Integer.toString(position);
                    try {
                        ArrayList<HoaDon> arrHdThang = hoaDonSql.LayHoaDonTheoThang(thang);
                        hoaDonList.removeAll(hoaDonList);
                        hoaDonList.addAll(arrHdThang);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewHoaDon.notifyDataSetChanged();
                }
                if (position >= 10) {
                    try {
                        ArrayList<HoaDon> arrHdThang = hoaDonSql.LayHoaDonTheoThang(Integer.toString(position));
                        hoaDonList.removeAll(hoaDonList);
                        hoaDonList.addAll(arrHdThang);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewHoaDon.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerViewHoaDon.onClickHoaDon(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                position1 = position;
                openDialog(Gravity.CENTER);
            }

            @Override
            public void onLongClick(View view, int position, boolean isLongClick) {

            }

        });
        return view;
    }

    private void openDialog(int gravity) {
        Dialog dialog = new Dialog(getActivity());
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
        HoaDonCtSql hoaDonCtSql = new HoaDonCtSql(sqlite);
        PetSql petSql = new PetSql(sqlite);
        ArrayList<HoaDonChiTiet> arrHdct = hoaDonCtSql.LayAllHdctTheoMaHd(hoaDonList.get(position1).getMaHoaDon());
        ArrayList<Pet> arrPet = new ArrayList<>();
        for (int i = 0; i < arrHdct.size(); i++) {
            Pet pet = new Pet();
            try {
                pet = petSql.LayPetTheoMa(arrHdct.get(i).getMaPet());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrPet.add(pet);
        }

        RecyclerViewPet rvHdct = new RecyclerViewPet(arrPet, "Chi tiết hóa đơn");
        recyclerView.setAdapter(rvHdct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}