package com.makienkovs.bullsandcows;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_guide);
        TextView textView1 = findViewById(R.id.textViewGuide1);
        textView1.setText(Html.fromHtml(getString(R.string.Guidetext1)));
        TextView textView2 = findViewById(R.id.textViewGuide2);
        textView2.setText(Html.fromHtml(getString(R.string.Guidetext2)));
        TextView textView21 = findViewById(R.id.textViewGuide21);
        textView21.setText(Html.fromHtml(getString(R.string.Guidetext21)));
        TextView textView3 = findViewById(R.id.textViewGuide3);
        textView3.setText(Html.fromHtml(getString(R.string.Guidetext3)));
        TextView textView4 = findViewById(R.id.textViewGuide4);
        textView4.setText(Html.fromHtml(getString(R.string.Guidetext4)));
        TextView textView5 = findViewById(R.id.textViewGuide5);
        textView5.setText(Html.fromHtml(getString(R.string.Guidetext5)));
    }
}