package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.scall.R;
import com.example.admin.scall.adapter.EffectAdapter;
import com.example.admin.scall.adapter.FontAdapter;
import com.example.admin.scall.adapter.IconAdapter;
import com.example.admin.scall.adapter.SelectedAdapter;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.waynell.library.DropAnimationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditNameActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvName;
    private EditText edtName;
    private RecyclerView rvFont;
    private RelativeLayout rlColor;
    private CircleImageView civColor;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerEffect;
    private RecyclerView.LayoutManager layoutManagerSelected;
    private RecyclerView.LayoutManager layoutManagerIcon;
    private String[] list;
    private FontAdapter adapter;
    private int currentColor;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private ImageView imgToolbar;
    private SeekBar sbTextSize;
    private ImageView imgEffect;
    private AdView adView;
    private TextView tvChangeBackground;
    private RecyclerView rvListSelected;
    private RecyclerView rvListIcon;
    private CheckBox checkBox;
    private CheckBox checkFull;
    //    private InfoStyle infoStyle;
    private String fontStyle;
    private int size;
    private Contact contact;
    private Animation animation;
    private RecyclerView rvEffect;
    private int[] listEffect = {0, R.anim.bounce, R.anim.rotate, R.anim.custom_anim1, R.anim.pump_top, R.anim.fade_in, R.anim.grow_from_bottom};
    private EffectAdapter effectAdapter;
    private SqliteHelper db;
    private List<InfoStyle> list1 = new ArrayList<>();
    private InfoStyle infoStyle;
    private String imagePath;
    private TextView tvAnotherEffect;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    private int animation1;
    private IconAdapter adapterIcon;
    private SelectedAdapter adapterSelected;
    private int[] listSelected = new int[6];
    private int[] listIcon = new int[23];
    private int posSelected;
    private DropAnimationView dropView;
    private TextView tvPreview;
    private String listIconString;
    private Gson gson;
    private boolean isAnother;
    private InfoStyle infoStyle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_edit_name);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        iniUI();
//        getData();
    }

    protected void iniUI() {
        gson = new Gson();
        listIcon = new int[]{R.mipmap.ic_love, R.mipmap.agnes_happy, R.mipmap.agnes_overjoyed, R.mipmap.agnes_sad, R.mipmap.edith,
                R.mipmap.gru, R.mipmap.gru2, R.mipmap.margo, R.mipmap.minion_amazed, R.mipmap.minion_angry, R.mipmap.minion_bananas,
                R.mipmap.minion_big, R.mipmap.minion_curious, R.mipmap.minion_dancing, R.mipmap.minion_duck, R.mipmap.minion_evil,
                R.mipmap.minion_evil2, R.mipmap.minion_evil_run, R.mipmap.minion_fruit, R.mipmap.minion_girl, R.mipmap.minion_golf,
                R.mipmap.minion_happy, R.mipmap.minion_jumping, R.mipmap.minion_kungfu, R.mipmap.minion_maid, R.mipmap.minion_please, R.mipmap.minion_sad,
                R.mipmap.minion_shout, R.mipmap.minion_tongue, R.mipmap.minion_wave, R.mipmap.minion_write,
                R.mipmap.emo1, R.mipmap.emo2, R.mipmap.emo3, R.mipmap.emo4, R.mipmap.emo5, R.mipmap.emo6, R.mipmap.emo7,
                R.mipmap.emo8, R.mipmap.emo9, R.mipmap.emo10, R.mipmap.emo11, R.mipmap.emo12, R.mipmap.emo13, R.mipmap.emo14, R.mipmap.emo15,
                R.mipmap.emo16, R.mipmap.emo17, R.mipmap.emo18, R.mipmap.emo19, R.mipmap.emo20, R.mipmap.emo21, R.mipmap.emo22, R.mipmap.emo23,
                R.mipmap.emo24, R.mipmap.emo25, R.mipmap.emo26, R.mipmap.emo27, R.mipmap.emo28, R.mipmap.emo29, R.mipmap.emo30, R.mipmap.emo31, R.mipmap.emo32,
                R.mipmap.emo33, R.mipmap.emo34, R.mipmap.emo35, R.mipmap.emo36, R.mipmap.emo37, R.mipmap.emo38, R.mipmap.emo39};
        db = new SqliteHelper(this);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        currentColor = ContextCompat.getColor(this, R.color.colorAccent);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sbTextSize = findViewById(R.id.sb_text_size);
        tvTitleToolbar = findViewById(R.id.tv_title_toolbar_2);
        dropView = findViewById(R.id.drop_animation_view);
        tvPreview = findViewById(R.id.tv_preview);
        tvTitleToolbar.setText(getString(R.string.make_your_style));
        imgToolbar = findViewById(R.id.img_menu_toolbar);
        imgToolbar.setImageResource(R.mipmap.ic_save);
        tvName = findViewById(R.id.tv_name);
        edtName = findViewById(R.id.edt_name);
        rvFont = findViewById(R.id.rv_font);
        rvEffect = findViewById(R.id.rv_effect);
        rvListIcon = findViewById(R.id.rv_list_icon);
        rvListSelected = findViewById(R.id.rv_list_selected);
        rlColor = findViewById(R.id.rl_color);
        civColor = findViewById(R.id.civ_color);
        imgEffect = findViewById(R.id.img_effect);
        tvAnotherEffect = findViewById(R.id.tv_another_effect);
        checkBox = findViewById(R.id.checkbox);
        checkFull = findViewById(R.id.check_full);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tvChangeBackground = findViewById(R.id.tv_change_background);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSelected = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerIcon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerEffect = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        rvListIcon.setLayoutManager(layoutManagerIcon);
        rvListSelected.setLayoutManager(layoutManagerSelected);
        rvEffect.setLayoutManager(layoutManagerEffect);
        tvName.measure(0, 0);
        checkFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    imgEffect.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    imgEffect.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAnother = b;
            }
        });
        if (getIntent() != null) {
            contact = (Contact) getIntent().getSerializableExtra("Contact");
            edtName.setText(contact.getName());
            tvName.setText(contact.getName());
            try {
                infoStyle = (InfoStyle) getIntent().getSerializableExtra("Style");
                tvName.setText(infoStyle.getName());
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                tvName.setTypeface(typeface);
                tvName.setTextSize(infoStyle.getSize());
                tvName.setTextColor(infoStyle.getColor());
                sbTextSize.setProgress(infoStyle.getSize());
                edtName.setText(infoStyle.getName());
            } catch (Exception e) {
                Log.e("iniUI: ", "iniUI: " + e.getMessage());
            }
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
                Log.d("click:", "click: " + value);
                tvName.setTypeface(font);
            }
        }, contact.getName());
        effectAdapter = new EffectAdapter(this, listEffect, new EffectAdapter.clickItem() {
            @Override
            public void click(int value, int pos) {
                if (pos == 0) {
                    if (animation1 != 0) {
                        tvName.clearAnimation();
                        animation1 = 0;
                    }
                } else {
                    if (animation != null) {
                        animation.cancel();
                        animation1 = value;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                value);
                        tvName.startAnimation(animation);
                    } else {
                        animation1 = value;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                value);
                        tvName.startAnimation(animation);
                    }
                }
            }
        });
        effectAdapter.notifyDataSetChanged();
        rvEffect.setAdapter(effectAdapter);
        adapter.notifyDataSetChanged();
        rvFont.setAdapter(adapter);
        rlColor.setOnClickListener(this);
        imgToolbar.setOnClickListener(this);
        tvChangeBackground.setOnClickListener(this);
        tvAnotherEffect.setOnClickListener(this);
        tvPreview.setOnClickListener(this);
        adapterIcon = new IconAdapter(this, listIcon, new IconAdapter.onClickItem() {
            @Override
            public void click(int values) {
                if (isAnother) {
                    if (posSelected == 6) {
                        posSelected = 0;
                    }
                    listSelected[posSelected] = values;


                    posSelected++;
                    adapterSelected = new SelectedAdapter(EditNameActivity.this, listSelected, new SelectedAdapter.onClick() {
                        @Override
                        public void click(int pos) {

                        }
                    });
                }
            }
        });
        rvListSelected.setAdapter(adapterSelected);
        adapterIcon.notifyDataSetChanged();
        rvListIcon.setAdapter(adapterIcon);
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
                if (infoStyle != null) {
                    currentColor = infoStyle.getColor();
                }
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
                if (isAnother) {
                    if (!checkIcon(listSelected)) {
                        listIconString = gson.toJson(listSelected);
                        InfoStyle infoStyle = new InfoStyle(contact.getId(), edtName.getText().toString(), formatNumber(contact.getPhoneNumber()),
                                fontStyle, imagePath, currentColor, size, animation1, listIconString);
                        saveAndUpdateStyle(infoStyle);
                    }
                } else {
                    InfoStyle infoStyle = new InfoStyle(contact.getId(), edtName.getText().toString(), formatNumber(contact.getPhoneNumber()),
                            fontStyle, imagePath, currentColor, size, animation1, listIconString);
                    saveAndUpdateStyle(infoStyle);
                }

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
            case R.id.tv_change_background:
                AlertDialog dialog2 = new AlertDialog.Builder(this)
                        .setMessage("Choose photo from")
                        .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, IMAGE_CAMERA);
                            }
                        })
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(EditNameActivity.this, ImagePickerActivity.class);
                                intent.putExtra(ImagePickerActivity.EXTRA_TYPE_PICKER,
                                        ImagePickerActivity.GALLERY_PICKER);
                                intent.putExtra(ImagePickerActivity.MODE, ImagePickerActivity.Mode.CROP);
                                startActivityForResult(intent, IMAGE_GALLERY);
                            }
                        }).show();
                break;
            case R.id.tv_another_effect:
                if (isAnother) {
                    if (!checkIcon(listSelected)) {
                        dropView.setDrawables(listSelected);
                        dropView.startAnimation();
                    } else {
                        Toast.makeText(this, "Chọn đúng 6 icon", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_preview:
                if (isAnother) {
                    if (!checkIcon(listSelected)) {
                        listIconString = gson.toJson(listSelected);
                        infoStyle2 = new InfoStyle(contact.getId(), edtName.getText().toString(), contact.getPhoneNumber(), fontStyle,
                                imagePath, currentColor, size, animation1, listIconString);
                    } else {
                        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    infoStyle2 = new InfoStyle(contact.getId(), edtName.getText().toString(), contact.getPhoneNumber(), fontStyle,
                            imagePath, currentColor, size, animation1, listIconString);
                }
                Intent i = new Intent(this, DetailContactActivity.class);
                i.putExtra("Main", "Abc");
                i.putExtra("InfoStyle", infoStyle2);
                startActivity(i);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY) {
            ImagePickerActivity.ImageReceiver receiver = new ImagePickerActivity.ImageReceiver(data);
            imagePath = receiver.getCroppedPath();
            Glide.with(this).load(imagePath).into(imgEffect);
        } else if (requestCode == IMAGE_CAMERA) {
            if (data.getData() == null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    final File file = savebitmap(photo);
                    imagePath = file.getPath();
                    Glide.with(this).load(imagePath).into(imgEffect);
                }
            }
        }
    }

    public static File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private boolean checkIcon(int[] array) {
        boolean check = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                check = true;
                break;
            } else {
                check = false;
            }
        }
        return check;
    }
}
