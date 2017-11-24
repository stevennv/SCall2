package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.model.Contact;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    private Contact contact;
    public TextView tvPreview;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView imgEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        iniUI();
    }

    private void iniUI() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        imgEffect = (ImageView) findViewById(R.id.img_effect);
        imgEffect.setOnClickListener(this);
        if (getIntent() != null) {
            contact = (Contact) getIntent().getSerializableExtra("Contact");
            tvName.setText(contact.getName());
            tvPhoneNumber.setText(contact.getPhoneNumber());
        }
        tvName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                Intent intent = new Intent(this, EditNameActivity.class);
                intent.putExtra("Contact", contact);
                startActivity(intent);
                break;
            case R.id.img_effect:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("Choose photo from")
                        .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
        }
    }
}
