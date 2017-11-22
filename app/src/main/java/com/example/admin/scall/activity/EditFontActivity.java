package com.example.admin.scall.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.admin.scall.R;
import com.example.admin.scall.fragment.FontFragment;
import com.example.admin.scall.fragment.PickColorFragment;

import java.io.IOException;

/**
 * Created by Admin on 11/21/2017.
 */

public class EditFontActivity extends AppCompatActivity {
    private String name;
    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    public TextView tvNamePreview;
    private EditText edtName;
    private String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_font);
        iniUI();
    }

    private void iniUI() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xffffffff);
        tabs.setIndicatorHeight(10);
        tabs.setBackgroundColor(0xff3F51B5);
        tabs.setTextColor(0xffffffff);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
        tvNamePreview = (TextView) findViewById(R.id.tv_name_preview);
        edtName = (EditText) findViewById(R.id.edt_name);
        if (getIntent() != null) {
            name = getIntent().getStringExtra("Name");
            edtName.setText(name);
            tvNamePreview.setText(name);
        }
        AssetManager assetManager = getAssets();
        try {
            list = assetManager.list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class ListPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {getString(R.string.font), getString(R.string.color)};

        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 1) {
                fragment = new FontFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArray("Font", list);
                fragment.setArguments(bundle);
                return fragment;
            } else {
                fragment = new PickColorFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
