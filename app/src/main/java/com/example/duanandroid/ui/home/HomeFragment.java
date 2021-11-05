package com.example.duanandroid.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duanandroid.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView1 = root.findViewById(R.id.title1);
        final TextView textView2 = root.findViewById(R.id.title2);

        String taiKhoan = getActivity().getIntent().getExtras().getString("TAIKHOAN");
        textView1.setText("Xin chào, " + taiKhoan);

        textView1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Pacifico.ttf"));
        textView2.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Pacifico.ttf"));

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });
        return root;
    }
}