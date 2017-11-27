package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.scall.R;
import com.example.admin.scall.adapter.EffectAdapter;
import com.example.admin.scall.adapter.FontAdapter;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditNameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName;
    private EditText edtName;
    private RecyclerView rvFont;
    private RelativeLayout rlColor;
    private CircleImageView civColor;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerEffect;
    private String[] list;
    private FontAdapter adapter;
    private int currentColor;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private ImageView imgToolbar;
    private SeekBar sbTextSize;
    private InfoStyle infoStyle;
    private String fontStyle;
    private int size;
    private Contact contact;
    private Animation animation;
    private RecyclerView rvEffect;
    private int[] listEffect = {R.anim.bounce, R.anim.rotate, R.anim.custom_anim1};
    private EffectAdapter effectAdapter;
    private SqliteHelper db;
    List<InfoStyle> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        iniUI();
//        getData();
    }

    protected void iniUI() {
        db = new SqliteHelper(this);

        list1 = db.getAllStyle();
        for (int i = 0; i < list1.size(); i++) {
            Log.d("iniUI: ", "iniUI: " + list1.get(i).getName() + "   " + list1.get(0).getPhone());
        }
//        Log.d("iniUI: ", "iniUI: " + db.getStyle/("096-891-2128").getName());
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
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
        rvEffect = (RecyclerView) findViewById(R.id.rv_effect);
        rlColor = (RelativeLayout) findViewById(R.id.rl_color);
        civColor = (CircleImageView) findViewById(R.id.civ_color);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerEffect = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        rvEffect.setLayoutManager(layoutManagerEffect);
        tvName.measure(0, 0);
        if (getIntent() != null) {
            contact = (Contact) getIntent().getSerializableExtra("Contact");
            edtName.setText(contact.getName());
            tvName.setText(contact.getName());
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
        effectAdapter = new EffectAdapter(this, listEffect, new EffectAdapter.clickItem() {
            @Override
            public void click(int value) {
                animation.cancel();
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        value);
                tvName.startAnimation(animation);
            }
        });
        effectAdapter.notifyDataSetChanged();
        rvEffect.setAdapter(effectAdapter);
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
                InfoStyle infoStyle = new InfoStyle(contact.getId(), contact.getName(), formatNumber(contact.getPhoneNumber()), fontStyle, currentColor, size);
//                if (db.getStyleCount() != 0) {

//        } else{
//            db.addStyle(infoStyle);
//                }
//                if (db.getContact(contact.getPhoneNumber())!=null){
//                    db.updateContact()
//                }
                saveAndUpdateStyle(infoStyle);
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

    private void saveAndUpdateStyle(InfoStyle info) {
        try {
            db.getStyleById(info.getPhone());
            db.updateStyle(info);
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            db.addStyle(info);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatNumber(String number) {
        String result1 = number.replace("-", "");
        String result2 = result1.replace("(", "");
        String result3 = result2.replace(")", "");
        String result = result3.replace(" ", "");

        return result;
    }
}
