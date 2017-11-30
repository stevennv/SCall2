package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.scall.R;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.waynell.library.DropAnimationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        iniUI();
    }

    private void iniUI() {

        db = new SqliteHelper(this);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        tvPreview = (RainbowTextView) findViewById(R.id.tv_preview);
        imgEffect = (ImageView) findViewById(R.id.img_effect);
        imgEndCall = (ImageView) findViewById(R.id.img_end_call);
        DropAnimationView view = (DropAnimationView) findViewById(R.id.drop_animation_view);
        view.setDrawables(listImage);
        view.startAnimation();
        imgEndCall.setOnClickListener(this);
        imgEffect.setOnClickListener(this);
        if (getIntent() != null) {
            String a = getIntent().getStringExtra("Main");
            if (a != null) {
                isCalling = true;
                contact = (Contact) getIntent().getSerializableExtra("Contact");
                if (contact != null) {
                    tvName.setText(contact.getName());
                    tvPhoneNumber.setText(contact.getPhoneNumber());
                } else {
                    tvName.setText("Ai đó");
                }
            } else {
                isCalling = false;
                InfoStyle infoStyle = (InfoStyle) getIntent().getSerializableExtra("Info");
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                tvName.setTypeface(font);
                tvName.setText(infoStyle.getName());
                tvName.setTextColor(infoStyle.getColor());
                tvName.setTextSize(infoStyle.getSize());
                Animation animation = AnimationUtils.loadAnimation(DetailContactActivity.this, infoStyle.getAnimation());
                tvName.startAnimation(animation);
                Glide.with(DetailContactActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                tvPreview.setVisibility(View.GONE);
                tvPhoneNumber.setText(infoStyle.getPhone() + "  " + getString(R.string.calling));
                tvPhoneNumber.setTypeface(font);
                tvPhoneNumber.setTextSize(24);
                tvPhoneNumber.setTextColor(infoStyle.getColor());
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
//        String phoneFormat = Utils.formatNumber(contact.getPhoneNumber());
//        try {
//            infoStyle = db.getStyleById(phoneFormat);
//            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
//            tvName.setTypeface(typeface);
//            tvName.setText(infoStyle.getName());
//            tvName.setTextColor(infoStyle.getColor());
//            tvName.setTextSize(infoStyle.getSize());
//            tvPhoneNumber.setText(infoStyle.getPhone());
//        } catch (Exception e) {
//            Log.d("iniUI", "iniUI: " + e.getMessage());
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                Intent intent = new Intent(this, EditNameActivity.class);
                intent.putExtra("Contact", contact);
                if (infoStyle != null) {
                    intent.putExtra("Style", infoStyle);
                }
                startActivity(intent);
                break;
            case R.id.img_effect:
                AlertDialog dialog = new AlertDialog.Builder(this)
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
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_GALLERY);
                            }
                        }).show();
                break;
            case R.id.img_end_call:
//

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
}
