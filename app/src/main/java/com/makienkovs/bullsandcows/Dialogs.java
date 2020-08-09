package com.makienkovs.bullsandcows;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class Dialogs {

    public static AlertDialog.Builder createDialog(Context c, String title, String msg) {
        return new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);
    }

    public static AlertDialog.Builder create(Context c) {
        return new AlertDialog.Builder(c)
                .setCancelable(false);
    }
}
