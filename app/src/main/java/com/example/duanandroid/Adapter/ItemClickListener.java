package com.example.duanandroid.Adapter;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
    void onLongClick(View view, int position,boolean isLongClick);
}
