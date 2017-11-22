package com.example.admin.scall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.model.ListStyleInfo;
import com.google.gson.Gson;

/**
 * Created by Admin on 11/22/2017.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mIntent = null;
    private static final String SHARE_NAME = "MAKE_YOUR_STYLE";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        mIntent = this;
        gson = new Gson();
    }

    public static SharedPreferencesUtils getIntent(Context context) {
        if (mIntent == null)
            mIntent = new SharedPreferencesUtils(context);
        return mIntent;
    }

    public void saveStyleInfo(InfoStyle infoStyle) {

        String json = gson.toJson(infoStyle);
        editor.putString("StyleInfo", json);
        editor.commit();
    }

    public InfoStyle getInfoStyle(String name) {
        String json = preferences.getString("StyleInfo", null);
        InfoStyle infoStyle = gson.fromJson(json, InfoStyle.class);
        return infoStyle;
    }

}
