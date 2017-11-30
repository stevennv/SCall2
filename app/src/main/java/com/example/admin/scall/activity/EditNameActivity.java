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
    //    private InfoStyle infoStyle;
    private String fontStyle;
    private int size;
    private Contact contact;
    private Animation animation;
    private RecyclerView rvEffect;
    private int[] listEffect = {R.anim.bounce, R.anim.rotate, R.anim.custom_anim1};
    private EffectAdapter effectAdapter;
    private SqliteHelper db;
    List<InfoStyle> list1 = new ArrayList<>();
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
        listIcon = new int[]{R.mipmap.ic_love, R.mipmap.agnes_happy, R.mipmap.agnes_overjoyed, R.mipmap.agnes_sad, R.mipmap.edith,
                R.mipmap.gru, R.mipmap.gru2, R.mipmap.margo, R.mipmap.minion_amazed, R.mipmap.minion_angry, R.mipmap.minion_bananas,
                R.mipmap.minion_big, R.mipmap.minion_curious, R.mipmap.minion_dancing, R.mipmap.minion_duck, R.mipmap.minion_evil,
                R.mipmap.minion_evil2, R.mipmap.minion_evil_run, R.mipmap.minion_fruit, R.mipmap.minion_girl, R.mipmap.minion_golf,
                R.mipmap.minion_happy, R.mipmap.minion_jumping, R.mipmap.minion_kungfu, R.mipmap.minion_maid, R.mipmap.minion_please, R.mipmap.minion_sad,
                R.mipmap.minion_shout, R.mipmap.minion_tongue, R.mipmap.minion_wave, R.mipmap.minion_write};
        db = new SqliteHelper(this);
//        list1 = db.getAllStyle();
//        for (int i = 0; i < list1.size(); i++) {
//            Log.d("iniUI: ", "iniUI: " + list1.get(i).getName() + "   " + list1.get(0).getPhone() +
//                    "   " + list1.get(i).getFont() + "    " + list1.get(i).getUrlImage() + "   " + list1.get(i).getAnimation());
//        }
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
        dropView = (DropAnimationView) findViewById(R.id.drop_animation_view);

        tvTitleToolbar.setText(getString(R.string.make_your_style));
        imgToolbar = (ImageView) findViewById(R.id.img_menu_toolbar);
        imgToolbar.setImageResource(R.mipmap.ic_save);
        tvName = (TextView) findViewById(R.id.tv_name);
        edtName = (EditText) findViewById(R.id.edt_name);
        rvFont = (RecyclerView) findViewById(R.id.rv_font);
        rvEffect = (RecyclerView) findViewById(R.id.rv_effect);
        rvListIcon = (RecyclerView) findViewById(R.id.rv_list_icon);
        rvListSelected = (RecyclerView) findViewById(R.id.rv_list_selected);
        rlColor = (RelativeLayout) findViewById(R.id.rl_color);
        civColor = (CircleImageView) findViewById(R.id.civ_color);
        imgEffect = (ImageView) findViewById(R.id.img_effect);
        tvAnotherEffect = (TextView) findViewById(R.id.tv_another_effect);
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tvChangeBackground = (TextView) findViewById(R.id.tv_change_background);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSelected = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerIcon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerEffect = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        rvListIcon.setLayoutManager(layoutManagerIcon);
        rvListSelected.setLayoutManager(layoutManagerSelected);
        rvEffect.setLayoutManager(layoutManagerEffect);
        tvName.measure(0, 0);
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
            public void click(int value) {
                animation.cancel();
                animation1 = value;
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
        tvChangeBackground.setOnClickListener(this);
        tvAnotherEffect.setOnClickListener(this);
        adapterIcon = new IconAdapter(this, listIcon, new IconAdapter.onClickItem() {
            @Override
            public void click(int values) {
                if (posSelected == 6) {
                    posSelected = 0;
                }
                listSelected[posSelected] = values;

                for (int i = 0; i < listSelected.length; i++) {
                    Log.d("iniUI123123_Selected", "click: " + listSelected[i]);
                }
                posSelected++;
                adapterSelected = new SelectedAdapter(EditNameActivity.this, listSelected);
                adapterSelected.notifyDataSetChanged();
                rvListSelected.setAdapter(adapterSelected);
//                adapterSelected = new SelectedAdapter(EditNameActivity.this, listSelected);
//                adapterSelected.notifyDataSetChanged();

            }
        });

        adapterIcon.notifyDataSetChanged();
        rvListIcon.setAdapter(adapterIcon);

        for (int i = 0; i < listSelected.length; i++) {
            Log.d("iniUI123123F", "iniUI: " + listSelected[i]);
        }
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
                Log.d("onClick: ", "onClick: " + formatNumber(contact.getPhoneNumber()));
                InfoStyle infoStyle = new InfoStyle(contact.getId(), edtName.getText().toString(), formatNumber(contact.getPhoneNumber()), fontStyle, imagePath, currentColor, size, animation1);
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
                InfoStyle infoStyle1 = db.getStyleById("6505551212");
                infoStyle1.getFont();
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
                dropView.setDrawables(listSelected);
                dropView.startAnimation();
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
}
