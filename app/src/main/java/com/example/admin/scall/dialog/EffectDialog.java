package com.example.admin.scall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.admin.scall.R;

/**
 * Created by Admin on 11/23/2017.
 */

public class EffectDialog extends Dialog {
    public EffectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_color);
    }
}
