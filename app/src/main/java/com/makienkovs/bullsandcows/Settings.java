package com.makienkovs.bullsandcows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_SOUND = "sound";
    public static final String APP_PREFERENCES_VIBRATION = "vibration";
    public static final String APP_PREFERENCES_TRAININGWINS = "trainingwins";
    public static final String APP_PREFERENCES_EASYWINS = "easywins";
    public static final String APP_PREFERENCES_HARDWINS = "hardwins";
    public static final String APP_PREFERENCES_TRAININGLOSE = "traininglose";
    public static final String APP_PREFERENCES_EASYLOSE = "easylose";
    public static final String APP_PREFERENCES_HARDLOSE = "hardlose";
    public static final String APP_PREFERENCES_BESTRAINING = "besttraining";
    public static final String APP_PREFERENCES_BESTEASY = "besteasy";
    public static final String APP_PREFERENCES_BESTHARD = "besthard";

    @SuppressLint("CommitPrefEdits")
    Settings (Context context){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public void writeStringParams(String name, String value) {
        editor.putString(name, value);
        editor.apply();
    }

    public void writeBooleanParams(String name, boolean value) {
        editor.putBoolean(name, value);
        editor.apply();
    }

    public String readStringParams(String name) {
        if (settings.contains(name)) {
            return settings.getString(name, "0");
        } else return "0";
    }

    public boolean readBooleanParams(String name) {
        if (settings.contains(name)) {
            return settings.getBoolean(name, true);
        } else return true;
    }
}

