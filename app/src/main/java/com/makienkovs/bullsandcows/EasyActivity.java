package com.makienkovs.bullsandcows;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EasyActivity extends AppCompatActivity {

    private Logic logic;
    private String output;
    private Buttons buttons;
    private boolean move = false;
    private boolean manMove = true;
    private String pcn;
    private String manNumber;
    private Sound sound;
    private Vibration vibration;
    String winText;
    String invalidInput;
    String manInput;
    private int manNumberCount;
    Settings settings;
    private int countOfMove = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
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
        countOfMove = 0;
        buttons = new Buttons(this);
        buttons.unsetChoiseButtonsListener();
        manNumberCount = 0;
        logic = new Logic();
        pcn = logic.getCompNumber();
        move = false;
        manMove = true;
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        TextView pc = findViewById(R.id.pc);
        TextView you = findViewById(R.id.autoCompleteTextView);
        winText = getString(R.string.Win);
        invalidInput = getString(R.string.Invalid);
        manInput = getString(R.string.Maninput);
        out.setText("");
        man.setText("");
        pc.setText("");
        you.setText(getString(R.string.Youshould));
    }


    public void inputNumber(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        Button button = (Button) v;
        String buttonNumber = button.getText().toString();
        if (move) {
            TextView infoTextView = findViewById(R.id.out);
            output = infoTextView.getText().toString();
            infoTextView.setText(buttons.numberInput(output, buttonNumber));
        } else if (manNumberCount < 4) {
            buttons.getcButtons()[manNumberCount].setText(buttonNumber);
            manNumberCount++;
        }
    }

    public void del(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        if (move) {
            TextView infoTextView = findViewById(R.id.out);
            output = infoTextView.getText().toString();
            infoTextView.setText(buttons.easyDel(output));
        } else if (manNumberCount > 0) {
            buttons.getcButtons()[manNumberCount - 1].setText("");
            manNumberCount--;
        }

    }

    @SuppressLint({"SetTextI18n"})
    public void ok(View v) {
        sound.playTap();
        vibration.vibrate(Vibration.VIBRATION_SHORT);
        TextView out = findViewById(R.id.out);
        TextView man = findViewById(R.id.man);
        TextView pc = findViewById(R.id.pc);
        TextView you = findViewById(R.id.autoCompleteTextView);

        String manText = man.getText().toString();
        String pcText = pc.getText().toString();
        output = out.getText().toString();

        if (!move) {
            manNumber = buttons.getChoiseButtonsNumber();

            if (logic.check(manNumber)) {
                move = true;
                message(manInput);
                sound.playMessage();
                you.setText(getString(R.string.Younumber));
            } else {
                message(invalidInput);
                sound.playMessage();
            }
        } else if (output.length() == 4) {
            countOfMove++;
            String[] result = logic.filter(pcn, output);
            output += " - " + result[0] + "B" + result[1] + "C";
            out.setText(output);
            if (result[0].equals("4")) {
                int countOfWins = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_EASYWINS));
                countOfWins++;
                settings.writeStringParams(Settings.APP_PREFERENCES_EASYWINS, "" + countOfWins);
                int prevBest = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_BESTEASY));
                if (countOfMove < prevBest || prevBest == 0) {
                    settings.writeStringParams(Settings.APP_PREFERENCES_BESTEASY, "" + countOfMove);
                }
                sound.playWin();
                vibration.vibrate(Vibration.VIBRATION_LONG);
                message(winText);
            }
        }
        //Видим результат компьютера, переводим его в поле человека, выводим результат компьютера
        else if (output.length() == 11 && manMove) {
            man.setText(manText + "\n" + output);
            output = logic.getCompChoice();
            String[] result = logic.filter(manNumber, output);
            output += result[0] + "B" + result[1] + "C";
            out.setText(output);
            manMove = false;
        }
        //Получаем предположение компьютера, и выводим быков и коров
        else if (output.length() == 11) {
            pc.setText(pcText + "\n" + output);
            out.setText("");
            String bulls = logic.takeManNumber(output);
            if (bulls.equals("4")) {
                int countOfLose = Integer.parseInt(settings.readStringParams(Settings.APP_PREFERENCES_EASYLOSE));
                countOfLose++;
                settings.writeStringParams(Settings.APP_PREFERENCES_EASYLOSE, "" + countOfLose);
                sound.playLose();
                vibration.vibrate(Vibration.VIBRATION_LONG);
                String manResult = man.getText().toString();
                String loseText = getString(R.string.Lose) + "\n" +
                        getString(R.string.PhoneChoose) + pcn + "\n" +
                        getString(R.string.View) + "\n" +
                        manResult + "\n" + "\n" +
                        getString(R.string.Replay);
                message(loseText);
            }
            manMove = true;
        }
    }

    private void message(String message) {
        if (message.equals(invalidInput) || message.equals(manInput)) {
            Dialogs.createDialog(EasyActivity.this, getString(R.string.Information), message)
                    .setPositiveButton(R.string.Yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sound.playTap();
                                    vibration.vibrate(Vibration.VIBRATION_SHORT);
                                }
                            })
                    .setIcon(R.drawable.info)
                    .create()
                    .show();
        } else {
            Dialogs.createDialog(EasyActivity.this, getString(R.string.End), message)
                    .setPositiveButton(R.string.Yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sound.playTap();
                                    vibration.vibrate(Vibration.VIBRATION_SHORT);
                                    newGame();
                                }
                            })
                    .setNegativeButton(R.string.No,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sound.playTap();
                                    vibration.vibrate(Vibration.VIBRATION_SHORT);
                                    finish();
                                }
                            })
                    .setIcon(R.drawable.info)
                    .create()
                    .show();
        }
    }
}