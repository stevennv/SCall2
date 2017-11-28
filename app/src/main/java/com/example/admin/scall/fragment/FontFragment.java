package com.example.admin.scall.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.EditFontActivity;
import com.example.admin.scall.adapter.FontAdapter;

/**
 * Created by Admin on 11/21/2017.
 */

public class FontFragment extends Fragment {
    private RecyclerView rvFont;
    private RecyclerView.LayoutManager layoutManager;
    private FontAdapter adapter;
    private String[] list;
    private String valuesFont;
    private EditFontActivity editFontActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editFontActivity = (EditFontActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_font, container, false);
        rvFont = view.findViewById(R.id.rv_font);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        list = getArguments().getStringArray("Font");
        adapter = new FontAdapter(getContext(), list, new FontAdapter.clickItem() {
            @Override
            public void click(String value) {
                valuesFont = value;
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + value);
                editFontActivity.tvNamePreview.setTypeface(font);
            }
        }, "123");
        adapter.notifyDataSetChanged();
        rvFont.setAdapter(adapter);
        return view;
    }


}
