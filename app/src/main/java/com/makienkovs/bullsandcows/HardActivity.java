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

public class HardActivity extends AppCompatActivity {

    private Logic logic;
    private String output;
    private Buttons buttons;
    private boolean move = true;
    private String pcn;
    private Sound sound;
    private Vibration vibration;
    Settings settings;
    private int countOfMove = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void newGame() {
        buttons = new Buttons(this);
        logic = new Logic();
        pcn = logic.getCompNumber();
        move = true;
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        TextView pc = findViewById(R.id.pc);
        TextView you = findViewById(R.id.autoCompleteTextView);
        out.setText("");
        man.setText("");
        pc.setText("");
        you.setText(R.string.Youcan);
        countOfMove = 0;
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
        if (move)
            infoTextView.setText(buttons.del(output));
        else
            infoTextView.setText(buttons.easyDel(output));
    }

    public void bc(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView infoTextView = findViewById(R.id.out);
        output = infoTextView.getText().toString();
        Button button = (Button) v;
        String sym = button.getText().toString();
        infoTextView.setText(buttons.bc(output, sym));
    }

    @SuppressLint({"SetTextI18n"})
    public void ok(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        TextView pc = findViewById(R.id.pc);
        String manText = man.getText().toString();
        String pcText = pc.getText().toString();
        output = out.getText().toString();

        //Ход человека, вводим 4 символа
        if (output.length() == 4 && move) {
            countOfMove++;
            String[] result = logic.filter(pcn, output);
            output += " - " + result[0] + "B" + result[1] + "C";
            out.setText(output);
            move = false;
            if (result[0].equals("4")) {
                int countOfWins = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_HARDWINS));
                countOfWins++;
                settings.writeStringParams(Settings.APP_PREFERENCES_HARDWINS, "" + countOfWins);
                int prevBest = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_BESTHARD));
                if (countOfMove < prevBest || prevBest == 0) {
                    settings.writeStringParams(Settings.APP_PREFERENCES_BESTHARD, "" + countOfMove);
                }
                String winText = getString(R.string.Win);
                message(winText);
                sound.playWin();
                vibration.vibrate(Vibration.VIBRATION_LONG);
            }
        }
        //Видим результат компьютера, переводим его в поле человека
        else if (output.length() == 11 && !move) {
            man.setText(manText + "\n" + output);
            output = logic.getCompChoice();
            out.setText(output);
            move = true;
        }
        //Получаем предположение компьютера, и вводим быков и коров
        else if (output.length() == 11) {
            pc.setText(pcText + "\n" + output);
            out.setText("");
            String bulls = logic.takeManNumber(output);
            if (bulls.equals("4")) {
                int countOfLose = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_HARDLOSE));
                countOfLose++;
                settings.writeStringParams(Settings.APP_PREFERENCES_HARDLOSE, "" + countOfLose);
                String loseText = getString(R.string.Lose) + "\n" +
                        getString(R.string.PhoneChoose) + pcn + "\n" +
                        getString(R.string.View) + "\n" +
                        manText + "\n" + "\n" +
                        getString(R.string.Replay);
                message(loseText);
                sound.playLose();
                vibration.vibrate(Vibration.VIBRATION_LONG);
            } else if (logic.validation()) {
                String errText = getString(R.string.Error) + "\n" +
                        getString(R.string.Answers) + "\n" +
                        pcText + "\n" +
                        output + "\n" + "\n" +
                        getString(R.string.Replay);
                message(errText);
                sound.playLose();
                vibration.vibrate(Vibration.VIBRATION_LONG);
            }
        }
    }

    private void message(String message) {
        Dialogs.createDialog(HardActivity.this, getString(R.string.End), message)
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