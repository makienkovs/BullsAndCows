package com.makienkovs.bullsandcows;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static boolean sound = true;
    private static boolean vibration = true;
    Sound soundButtons;
    Settings settings;
    Vibration vibrationButtons;
    String trainingWins;
    String trainingLose;
    String easyWins;
    String easyLose;
    String hardWins;
    String hardLose;
    String trainingBest;
    String easyBest;
    String hardBest;

    @SuppressLint({"SourceLockedOrientationActivity", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        settings = new Settings(this);
        soundButtons = new Sound(this, true);
        vibrationButtons = new Vibration(this, true);
        readParams();
    }

    static boolean getSound() {
        return sound;
    }

    static boolean getVibration() {
        return vibration;
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeParams();
        soundButtons.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readParams();
        soundButtons = new Sound(this, true);
    }

    private void readParams() {
        sound = settings.readBooleanParams(Settings.APP_PREFERENCES_SOUND);
        vibration = settings.readBooleanParams(Settings.APP_PREFERENCES_VIBRATION);

        trainingWins = settings.readStringParams(Settings.APP_PREFERENCES_TRAININGWINS);
        trainingLose = settings.readStringParams(Settings.APP_PREFERENCES_TRAININGLOSE);

        easyWins = settings.readStringParams(Settings.APP_PREFERENCES_EASYWINS);
        easyLose = settings.readStringParams(Settings.APP_PREFERENCES_EASYLOSE);

        hardWins = settings.readStringParams(Settings.APP_PREFERENCES_HARDWINS);
        hardLose = settings.readStringParams(Settings.APP_PREFERENCES_HARDLOSE);

        trainingBest = settings.readStringParams(Settings.APP_PREFERENCES_BESTRAINING);
        easyBest = settings.readStringParams(Settings.APP_PREFERENCES_BESTEASY);
        hardBest = settings.readStringParams(Settings.APP_PREFERENCES_BESTHARD);
    }

    private void writeParams() {
        settings.writeBooleanParams(Settings.APP_PREFERENCES_SOUND, sound);
        settings.writeBooleanParams(Settings.APP_PREFERENCES_VIBRATION, vibration);
    }

    public void setSound() {
        sound = !sound;
        if (sound)
            soundButtons.playMessage();
        else
            soundButtons.playTap();
    }

    public void setVibration() {
        vibration = !vibration;
        if (vibration)
            vibrationButtons.vibrate(Vibration.VIBRATION_LONG);
        else
            vibrationButtons.vibrate(Vibration.VIBRATION_SHORT);
    }

    private void fx() {
        if (sound)
            soundButtons.playTap();
        if (vibration)
            vibrationButtons.vibrate(Vibration.VIBRATION_SHORT);
    }

    public void play(final View v) {
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        v.startAnimation(animScale);
        fx();

        final Intent training = new Intent(MainActivity.this, TrainingActivity.class);
        final Intent easy = new Intent(MainActivity.this, EasyActivity.class);
        final Intent hard = new Intent(MainActivity.this, HardActivity.class);

        final String[] levels = {getString(R.string.Training), getString(R.string.Easy), getString(R.string.Hard)};
        final int[] level = new int[1];
        Dialogs.create(this)
                .setTitle(getString(R.string.Level))
                .setIcon(R.drawable.brain)
                .setSingleChoiceItems(levels, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fx();
                        level[0] = i;
                    }
                })
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fx();
                        switch (level[0]) {
                            case 0:
                                startActivity(training);
                                break;
                            case 1:
                                startActivity(easy);
                                break;
                            case 2:
                                startActivity(hard);
                                break;
                        }
                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fx();
                    }
                })
                .create()
                .show();
    }

    public void guide(View v) {
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        v.startAnimation(animScale);
        fx();
        Intent guide = new Intent(MainActivity.this, GuideActivity.class);
        startActivity(guide);
    }

    private void resetStat() {
        Dialogs.create(this)
                .setTitle(R.string.Information)
                .setIcon(R.drawable.info)
                .setMessage(R.string.Sure)
                .setNegativeButton(R.string.Cancel, null)
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settings.writeStringParams(Settings.APP_PREFERENCES_TRAININGWINS, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_TRAININGLOSE, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_EASYWINS, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_EASYLOSE, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_HARDWINS, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_HARDLOSE, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_BESTRAINING, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_BESTEASY, "0");
                        settings.writeStringParams(Settings.APP_PREFERENCES_BESTHARD, "0");
                        readParams();
                    }
                })
                .create()
                .show();
    }

    @SuppressLint("SetTextI18n")
    public void statictic(View v) {
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        v.startAnimation(animScale);
        fx();

        final View statsView = getLayoutInflater().inflate(R.layout.stats, null);

        final TextView trainingWinsStat = statsView.findViewById(R.id.trainingStats);
        trainingWinsStat.setText(getString(R.string.Wins) + " - " + trainingWins + "\n" + getString(R.string.Losses) + " - " + trainingLose + "\n" + getString(R.string.Bestscore) + " - " + trainingBest);
        final TextView easyWinsStat = statsView.findViewById(R.id.easyStats);
        easyWinsStat.setText(getString(R.string.Wins) + " - " + easyWins + "\n" + getString(R.string.Losses) + " - " + easyLose + "\n" + getString(R.string.Bestscore) + " - " + easyBest);
        final TextView hardWinsStat = statsView.findViewById(R.id.hardStats);
        hardWinsStat.setText(getString(R.string.Wins) + " - " + hardWins + "\n" + getString(R.string.Losses) + " - " + hardLose + "\n" + getString(R.string.Bestscore) + " - " + hardBest);

        Dialogs.create(this)
                .setTitle(R.string.Statistics)
                .setIcon(R.drawable.stats)
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fx();
                    }
                })
                .setNeutralButton(R.string.Reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetStat();
                    }
                })
                .setView(statsView)
                .create()
                .show();
    }

    public void settings(View v) {
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        v.startAnimation(animScale);
        fx();

        final View settingsView = getLayoutInflater().inflate(R.layout.settings, null);
        final Switch soundSwitch = settingsView.findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(sound);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSound();
            }
        });

        final Switch vibrationSwitch = settingsView.findViewById(R.id.vibrationSwitch);
        vibrationSwitch.setChecked(vibration);
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVibration();
            }
        });

        Dialogs.create(this)
                .setTitle(R.string.Settings)
                .setPositiveButton(R.string.Ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fx();
                                sound = soundSwitch.isChecked();
                                vibration = vibrationSwitch.isChecked();
                            }
                        })
                .setView(settingsView)
                .setIcon(R.drawable.settings)
                .create()
                .show();
    }
}