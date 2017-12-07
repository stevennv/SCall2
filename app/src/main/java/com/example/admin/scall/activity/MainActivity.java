package com.example.admin.scall.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.scall.R;
import com.example.admin.scall.adapter.ContactAdapter;
import com.example.admin.scall.dialog.ConfirmQuitDialog;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtSearch;
    private ImageView imgSearch;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private RecyclerView rvContact;
    private RecyclerView.LayoutManager layoutManager;
    private ContactAdapter adapter;
    private List<Contact> list = new ArrayList<>();
    private List<InfoStyle> list1 = new ArrayList<>();
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int RECORD_REQUEST_CODE = 101;
    private AdView adView;
    private ConfirmQuitDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);
        iniUI();
    }

    private void iniUI() {
        Log.d("iniUI:", "iniUI: "+ db.getStyleByPhone("+841664388560"));
        List<InfoStyle> list = db.getAllStyle();
//        for (int i = 0; i < list.size(); i++) {
//            Log.d("iniUI:F", "iniUI: " + list.get(i).getName() + "   " + list.get(i).getPhone() + "   " + list.get(i).getId());
//        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTitleToolbar = findViewById(R.id.tv_title_toolbar);
        tvTitleToolbar.setText(getString(R.string.app_name));
        edtSearch = findViewById(R.id.edt_search);
        imgSearch = findViewById(R.id.img_search);
        rvContact = (RecyclerView) findViewById(R.id.rv_contact);
        layoutManager = new LinearLayoutManager(this);
        rvContact.setLayoutManager(layoutManager);
        imgSearch.setOnClickListener(this);
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = edtSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String mPhoneNumber = tMgr.getLine1Number();
//        Log.d("iniUI:", "iniUI: " + mPhoneNumber);
//        mPhoneNumber.length();
        // 15555215554
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, Phone.DISPLAY_NAME + " ASC");
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                int id1 = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("getContactList: ", "getContactList: " + id1);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            Phone.CONTENT_URI,
                            null,
                            Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                Phone.NUMBER));
                        Log.i("GET_INFO", "Name: " + name);
                        Log.i("GET_INFO", "Phone Number: " + phoneNo);
                        Contact contact = new Contact(id1, phoneNo, name);
                        list.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        adapter = new ContactAdapter(MainActivity.this, list);
        adapter.notifyDataSetChanged();
        rvContact.setAdapter(adapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                    return;
                }
            }
        }
    }


    @Override
    public void onBackPressed() {

//        Toast.makeText(getApplicationContext(), "hehehehe " + count, Toast.LENGTH_SHORT).show();
//        count++;
        dialog = new ConfirmQuitDialog(this, getString(R.string.confirm_quit), getString(R.string.ok),
                getString(R.string.cancel), new ConfirmQuitDialog.clickBtn1() {
            @Override
            public void click() {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        }, new ConfirmQuitDialog.clickBtn2() {
            @Override
            public void click() {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
       /* AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("ádasdasd")
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();*/
//        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }
}
