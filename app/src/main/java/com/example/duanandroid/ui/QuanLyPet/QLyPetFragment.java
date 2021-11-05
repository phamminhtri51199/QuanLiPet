package com.example.duanandroid.ui.QuanLyPet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.duanandroid.Adapter.RecyclerViewQlyPet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;
import com.example.duanandroid.ThemPetActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;

public class QLyPetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public RecyclerView recyclerView;
    FloatingActionButton fabThemPet;
    ArrayList<Pet> petList;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Spinner spnKho;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerViewQlyPet recyclerViewQlyPet;

    boolean isExpanded = true;

    public QLyPetFragment() {// Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static QLyPetFragment newInstance(String param1, String param2) {
        QLyPetFragment fragment = new QLyPetFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qlypet, container, false);
        recyclerView = view.findViewById(R.id.rvKho);
        fabThemPet = view.findViewById(R.id.fabThemPet);
        appBarLayout = view.findViewById(R.id.AppBarLayout);
        collapsingToolbarLayout = view.findViewById(R.id.CollapsingToolbarLayout);
        spnKho = view.findViewById(R.id.spnKho);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.color_toolbar));

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrThang);
        spnKho.setAdapter(adapter);
        initToolBarAnim();
        Sqlite sqlite = new Sqlite(getContext());
        PetSql petSql = new PetSql(sqlite);
        try {
            petList = petSql.LayAllPet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_down_to_up);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        recyclerViewQlyPet = new RecyclerViewQlyPet(petList);
        recyclerView.setAdapter(recyclerViewQlyPet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        spnKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    try {
                        ArrayList<Pet> arrVatnuoi = petSql.LayAllPet();
                        petList.removeAll(petList);
                        petList.addAll(arrVatnuoi);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewQlyPet.notifyDataSetChanged();
                    recyclerView.setLayoutAnimation(layoutAnimationController);

                }
                if (position > 0 && position < 10) {
                    String thang = "0" + Integer.toString(position);
                    try {
                        ArrayList<Pet> arrVatnuoi = petSql.LayAllPetTheoThang(thang);
                        petList.removeAll(petList);
                        petList.addAll(arrVatnuoi);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewQlyPet.notifyDataSetChanged();
                    recyclerView.setLayoutAnimation(layoutAnimationController);

                }
                if (position >= 10) {
                    try {
                        ArrayList<Pet> arrVatnuoi = petSql.LayAllPetTheoThang(Integer.toString(position));
                        petList.removeAll(petList);
                        petList.addAll(arrVatnuoi);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    recyclerViewQlyPet.notifyDataSetChanged();
                    recyclerView.setLayoutAnimation(layoutAnimationController);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fabThemPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemPetActivity.class));
            }
        });

        return view;
    }


    private void initToolBarAnim() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.color_toolbar));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.black_trans));
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    isExpanded = false;
                } else {
                    isExpanded = true;
                }
                getActivity().invalidateOptionsMenu();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Sqlite sqlite = new Sqlite(getContext());
                PetSql petSql = new PetSql(sqlite);
                try {
                    petList = petSql.LayAllPet();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                recyclerViewQlyPet = new RecyclerViewQlyPet(petList);
                recyclerView.setAdapter(recyclerViewQlyPet);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }
}