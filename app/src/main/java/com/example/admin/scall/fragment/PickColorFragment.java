package com.example.admin.scall.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.scall.R;
import com.skydoves.multicolorpicker.ColorEnvelope;
import com.skydoves.multicolorpicker.MultiColorPickerView;
import com.skydoves.multicolorpicker.listeners.ColorListener;

/**
 * Created by Admin on 11/21/2017.
 */

public class PickColorFragment extends Fragment {
    private MultiColorPickerView pickColor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_color, container, false);
        pickColor = view.findViewById(R.id.multiColorPickerView);
        Drawable drawable = getContext().getDrawable(R.drawable.palette);
//        pickColor.addSelector(drawable, new ColorListener() {
//            @Override
//            public void onColorSelected(ColorEnvelope envelope) {
//                int color = envelope.getColor();
//            }
//        });
        return view;
    }
}
