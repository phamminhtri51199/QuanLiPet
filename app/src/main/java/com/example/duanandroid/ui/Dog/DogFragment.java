package com.example.duanandroid.ui.Dog;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.duanandroid.Adapter.RecyclerViewPet;
import com.example.duanandroid.ModelClass.Pet;
import com.example.duanandroid.R;
import com.example.duanandroid.Sql.Sqlite;
import com.example.duanandroid.Sql.PetSql;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DogFragment extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    View view;
    Sqlite sqlite;
    PetSql petSql;
    ArrayList<Pet> petList;

    public DogFragment() {
        // Required empty public constructor
    }

    public static DogFragment newInstance(String param1, String param2) {
        DogFragment fragment = new DogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dog, container, false);

        recyclerView = view.findViewById(R.id.rvCho);
        searchView = view.findViewById(R.id.svCho);

        sqlite = new Sqlite(getActivity());
        petSql = new PetSql(sqlite);
        try {
            petList = petSql.LayAllCho();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Pet> vatNuois = new ArrayList<>();
        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getTrangThai().equalsIgnoreCase("Chưa bán")) {
                vatNuois.add(petList.get(i));
            }
        }

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_left_to_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        String tenTk = getActivity().getIntent().getExtras().getString("TAIKHOAN");

        RecyclerViewPet recyclerViewPet = new RecyclerViewPet(vatNuois, tenTk);
        recyclerView.setAdapter(recyclerViewPet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setFocusable(false);

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerViewPet.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewPet.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }
}