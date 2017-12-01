package com.example.admin.scall.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.scall.R;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;
import com.example.admin.scall.utils.Utils;
import com.google.gson.Gson;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.waynell.library.DropAnimationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    private Contact contact;
    public RainbowTextView tvPreview;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView imgEffect;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    private SqliteHelper db;
    private InfoStyle infoStyle;
    private ImageView imgEndCall;
    private boolean isCalling;
    private int[] listImage = new int[6];
    private DropAnimationView dropView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_contact);
        iniUI();
    }

    private void iniUI() {
        gson = new Gson();
        db = new SqliteHelper(this);
        tvName = findViewById(R.id.tv_name);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvPreview = findViewById(R.id.tv_preview);
        imgEffect = findViewById(R.id.img_effect);
        imgEndCall = findViewById(R.id.img_end_call);
        dropView = findViewById(R.id.drop_animation_view);
        imgEndCall.setOnClickListener(this);
        imgEffect.setOnClickListener(this);
        if (getIntent() != null) {
            String a = getIntent().getStringExtra("Main");
            if (a != null) {
                isCalling = true;
                infoStyle = (InfoStyle) getIntent().getSerializableExtra("InfoStyle");
                if (infoStyle.getFont() != null) {
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font);
                    tvPhoneNumber.setTypeface(font);
                }
                tvName.setText(infoStyle.getName());
                tvName.setTextColor(infoStyle.getColor());
                tvName.setTextSize(infoStyle.getSize());
                if (infoStyle.getAnimation() != 0) {
                    Animation animation = AnimationUtils.loadAnimation(DetailContactActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation);
                }
                Glide.with(DetailContactActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                tvPreview.setVisibility(View.GONE);
                tvPhoneNumber.setText(infoStyle.getPhone() + "  " + getString(R.string.calling));
                tvPhoneNumber.setTextSize(24);
                tvPhoneNumber.setTextColor(infoStyle.getColor());
                if (infoStyle.getListIcon() != null) {
                    listImage = gson.fromJson(infoStyle.getListIcon(), int[].class);
                    dropView.setDrawables(listImage);
                    dropView.startAnimation();
                }
            } else {
                isCalling = false;
                InfoStyle infoStyle = (InfoStyle) getIntent().getSerializableExtra("Info");
                tvName.setText(infoStyle.getName());
                tvName.setTextColor(infoStyle.getColor());
                tvName.setTextSize(infoStyle.getSize());
                Glide.with(DetailContactActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                tvPreview.setVisibility(View.GONE);
                tvPhoneNumber.setText(infoStyle.getPhone() + "  " + getString(R.string.calling));
                tvPhoneNumber.setTextSize(24);
                tvPhoneNumber.setTextColor(infoStyle.getColor());
                if (infoStyle.getFont() != null) {
                    Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font1);
                    tvPhoneNumber.setTypeface(font1);
                }
                if (infoStyle.getAnimation() != 0) {
                    Animation animation1 = AnimationUtils.loadAnimation(DetailContactActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation1);
                }
                if (infoStyle.getListIcon() != null) {
                    listImage = gson.fromJson(infoStyle.getListIcon(), int[].class);
                    dropView.setDrawables(listImage);
                    dropView.startAnimation();
                }
//                Uri uri = Uri.parse(infoStyle.getUrlImage());
//                imgEffect.setImageURI(uri);
//                imgEffect.setImageURI(infoStyle.getUrlImage());
                tvName.setClickable(false);
                imgEffect.setClickable(false);
                tvName.setEnabled(false);
                imgEffect.setEnabled(false);
            }
        }
        tvName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:

                break;
            case R.id.img_effect:
                break;
            case R.id.img_end_call:
                Utils.shareApp(this);
//                takeScreenshot();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY) {

        } else if (requestCode == IMAGE_CAMERA) {
            if (data.getData() == null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    final File file = savebitmap(photo);
                    String mImagePath = file.getPath();
                    Glide.with(this).load(mImagePath).into(imgEffect);
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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Scall/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Uri url = Uri.fromFile(imageFile);
        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM,
                Uri.fromFile(new File(url.toString())));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "share an image");
        intent.putExtra(Intent.EXTRA_TEXT,
                "This is an image to share with you");
        try {
            startActivity(Intent.createChooser(intent,
                    "ShareThroughChooser Test"));
        } catch (android.content.ActivityNotFoundException ex) {
            (new android.app.AlertDialog.Builder(this)
                    .setMessage("Share failed")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                }
                            }).create()).show();
        }

//        intent.setDataAndType(url, "image/*");
//        startActivity(intent);
    }

}
