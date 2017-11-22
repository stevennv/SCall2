package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.adapter.FontAdapter;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SharedPreferencesUtils;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditNameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName;
    private EditText edtName;
    private RecyclerView rvFont;
    private RelativeLayout rlColor;
    private CircleImageView civColor;
    private RecyclerView.LayoutManager layoutManager;
    private String[] list;
    private FontAdapter adapter;
    private int currentColor;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private ImageView imgToolbar;
    private SeekBar sbTextSize;
    private SharedPreferencesUtils utils;
    private InfoStyle infoStyle;
    private String fontStyle;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        iniUI();
        getData();
    }

    protected void iniUI() {
        utils = new SharedPreferencesUtils(this);
        currentColor = ContextCompat.getColor(this, R.color.colorAccent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sbTextSize = (SeekBar) findViewById(R.id.sb_text_size);
        tvTitleToolbar = (TextView) findViewById(R.id.tv_title_toolbar_2);
        tvTitleToolbar.setText(getString(R.string.make_your_style));
        imgToolbar = (ImageView) findViewById(R.id.img_menu_toolbar);
        imgToolbar.setImageResource(R.mipmap.ic_save);
        tvName = (TextView) findViewById(R.id.tv_name);
        edtName = (EditText) findViewById(R.id.edt_name);
        rvFont = (RecyclerView) findViewById(R.id.rv_font);
        rlColor = (RelativeLayout) findViewById(R.id.rl_color);
        civColor = (CircleImageView) findViewById(R.id.civ_color);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        tvName.measure(0, 0);
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("Name");
            edtName.setText(name);
            tvName.setText(name);
        }
        AssetManager assetManager = getAssets();
        try {
            list = assetManager.list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new FontAdapter(this, list, new FontAdapter.clickItem() {
            @Override
            public void click(String value) {
                fontStyle = value;
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + value);
                tvName.setTypeface(font);
            }
        });
        adapter.notifyDataSetChanged();
        rvFont.setAdapter(adapter);
        rlColor.setOnClickListener(this);
        imgToolbar.setOnClickListener(this);
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvName.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvName.setTextSize(i);
                size = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_color:
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentColor = color;
                        civColor.setBackgroundColor(currentColor);
                        tvName.setTextColor(currentColor);
                    }
                });
                dialog.show();
                break;
            case R.id.img_menu_toolbar:
                infoStyle = new InfoStyle("1", edtName.getText().toString(), fontStyle, currentColor, size);
                utils.saveStyleInfo(infoStyle);
                AlertDialog dialog1 = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.save_success))
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        }).show();
                break;
        }
    }

    private void getData() {
        if (utils.getInfoStyle("1") != null) {
            infoStyle = utils.getInfoStyle("1");
            if (infoStyle.getFont() != null) {
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                tvName.setTypeface(font);
            }
            if (infoStyle.getSize() != 0) {
                tvName.setTextSize(infoStyle.getSize());
            }

            tvName.setText(infoStyle.getName());

            tvName.setTextColor(infoStyle.getColorName());
        } else {
            tvName.setTextSize(16);
        }
    }
}
