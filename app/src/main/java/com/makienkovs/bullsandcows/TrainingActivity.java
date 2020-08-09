package com.makienkovs.bullsandcows;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {

    private Logic logic;
    private String output;
    private Buttons buttons;
    private String pcn;
    private Sound sound;
    private Vibration vibration;
    Settings settings;
    private int countOfMove = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sound = new Sound(this, MainActivity.getSound());
        vibration = new Vibration(this, MainActivity.getVibration());
        settings = new Settings(this);
        newGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sound.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sound = new Sound(this, MainActivity.getSound());
    }

    @SuppressLint("SetTextI18n")
    private void newGame() {
        buttons = new Buttons(this);
        logic = new Logic();
        pcn = logic.getCompNumber();
        countOfMove = 0;
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        out.setText("");
        man.setText("");
    }

    public void inputNumber(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView infoTextView = findViewById(R.id.out);
        output = infoTextView.getText().toString();
        Button button = (Button) v;
        String buttonNumber = button.getText().toString();
        infoTextView.setText(buttons.numberInput(output, buttonNumber));
    }

    public void del(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView infoTextView = findViewById(R.id.out);
        output = infoTextView.getText().toString();
        infoTextView.setText(buttons.easyDel(output));
    }

    @SuppressLint({"SetTextI18n"})
    public void ok(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        String manText = man.getText().toString();
        output = out.getText().toString();

        if (output.length() == 4) {
            String[] result = logic.filter(pcn, output);
            output += " - " + result[0] + "B" + result[1] + "C";
            countOfMove++;
            out.setText(output);
            if (result[0].equals("4")) {
                int countOfWins = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_TRAININGWINS));
                countOfWins++;
                settings.writeStringParams(Settings.APP_PREFERENCES_TRAININGWINS, "" + countOfWins);
                int prevBest = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_BESTRAINING));
                if (countOfMove < prevBest || prevBest == 0) {
                    settings.writeStringParams(Settings.APP_PREFERENCES_BESTRAINING, "" + countOfMove);
                }
                String winText = getString(R.string.Win);
                message(winText);
                sound.playWin();
                vibration.vibrate(Vibration.VIBRATION_LONG);
            } else if (countOfMove >= 10) {
                int countOfLose = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_TRAININGLOSE));
                countOfLose++;
                settings.writeStringParams(Settings.APP_PREFERENCES_TRAININGLOSE, "" + countOfLose);
                String loseText = getString(R.string.Lose) + "\n" +
                        getString(R.string.PhoneChoose) + pcn + "\n" +
                        getString(R.string.View) + "\n" +
                        manText + "\n" +
                        countOfMove + ". " + output + "\n" + "\n" +
                        getString(R.string.Replay);
                message(loseText);
                sound.playLose();
                vibration.vibrate(Vibration.VIBRATION_LONG);
            }
        } else if (output.length() == 11) {
            out.setText("");
            man.setText(manText + "\n" + countOfMove + ". " + output);
        }
    }

    private void message(String message) {

        Dialogs.createDialog(TrainingActivity.this, getString(R.string.End), message)
                .setPositiveButton(R.string.Yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vibration.vibrate(Vibration.VIBRATION_SHORT);
                                newGame();
                            }
                        }).setNegativeButton(R.string.No,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vibration.vibrate(Vibration.VIBRATION_SHORT);
                        finish();
                    }
                })
                .setIcon(R.drawable.info)
                .create()
                .show();
    }
}
