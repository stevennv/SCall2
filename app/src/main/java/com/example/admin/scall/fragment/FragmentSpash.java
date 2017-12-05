package com.example.admin.scall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.MainActivity;
import com.example.admin.scall.activity.OnBoardingActivity;
import com.example.admin.scall.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 12/5/2017.
 */

public class FragmentSpash extends Fragment implements View.OnClickListener {
    private Button btnStart;
    private Button btnNext;
    private int check;
    private OnBoardingActivity mActivity;
    private SharedPreferencesUtils utils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_spash, container, false);
        mActivity = (OnBoardingActivity) getContext();
        utils = new SharedPreferencesUtils(getContext());
        btnNext = view.findViewById(R.id.btn_next);
        btnStart = view.findViewById(R.id.btn_start);
        if (getArguments() != null) {
            check = getArguments().getInt("edttext");
            if (check == 3) {
                btnStart.setVisibility(View.VISIBLE);
                int bottomOfScreen = getResources().getDisplayMetrics()
                        .heightPixels / 2;
                btnStart.animate()
                        .translationY(bottomOfScreen)
                        .setInterpolator(new AccelerateInterpolator())
                        .setInterpolator(new BounceInterpolator())
                        .setDuration(2000);
            } else {
                btnNext.setVisibility(View.VISIBLE);
            }
        }
        btnNext.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(getContext(), MainActivity.class);
                utils.saveFirstOpenApp(false);
                startActivity(intent);
                break;
            case R.id.btn_next:
                mActivity.viewPager.setCurrentItem(mActivity.viewPager.getCurrentItem() + 1);
                break;
        }
    }
}
