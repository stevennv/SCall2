package com.example.admin.scall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.scall.R;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Admin on 11/21/2017.
 */

public class PickColorFragment extends Fragment {
    private int currentColor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_color, container, false);
        currentColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), currentColor, true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
            }
        });
        dialog.show();
        return view;
    }
}
