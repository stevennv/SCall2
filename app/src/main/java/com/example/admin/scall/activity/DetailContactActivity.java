package com.example.admin.scall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.model.Contact;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    private Contact contact;
    public TextView tvPreview;
    private TextView tvName;
    private TextView tvPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        iniUI();
    }

    private void iniUI() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
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
                intent.putExtra("Name", tvName.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
