package com.makienkovs.bullsandcows;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class Buttons {
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button[] buttons;
    private String[] buttonColors;
    private Button cb1;
    private Button cb2;
    private Button cb3;
    private Button cb4;
    private Button[] cButtons;

    Buttons(EasyActivity view) {
        //Кнопки помошники
        button0 = view.findViewById(R.id.hb0);
        button1 = view.findViewById(R.id.hb1);
        button2 = view.findViewById(R.id.hb2);
        button3 = view.findViewById(R.id.hb3);
        button4 = view.findViewById(R.id.hb4);
        button5 = view.findViewById(R.id.hb5);
        button6 = view.findViewById(R.id.hb6);
        button7 = view.findViewById(R.id.hb7);
        button8 = view.findViewById(R.id.hb8);
        button9 = view.findViewById(R.id.hb9);
        buttons = new Button[]{button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
        buttonColors = new String[]{"white", "white", "white", "white", "white", "white", "white", "white", "white", "white"};
        for (final Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonNumber = Integer.parseInt(button.getText().toString());
                    String color = buttonColors[buttonNumber];
                    if (color.equals("white")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        buttonColors[buttonNumber] = "red";
                    } else if (color.equals("red")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(0, 153, 205)));
                        buttonColors[buttonNumber] = "blue";
                    } else {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
                        buttonColors[buttonNumber] = "white";
                    }
                }
            });
        }
        //Кнопки для ввода данных
        cb1 = view.findViewById(R.id.cb1);
        cb2 = view.findViewById(R.id.cb2);
        cb3 = view.findViewById(R.id.cb3);
        cb4 = view.findViewById(R.id.cb4);
        cButtons = new Button[]{cb1, cb2, cb3, cb4};
        for (final Button button : cButtons) {
            button.setText("");
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    String buttonNumber = button.getText().toString();
                    if (buttonNumber.equals("")) button.setText("0");
                    else if (Integer.parseInt(buttonNumber) == 9) button.setText("");
                    else {
                        int bn = Integer.parseInt(buttonNumber);
                        bn++;
                        button.setText("" + bn);
                    }
                }
            });
        }
    }

    public Button[] getcButtons() {
        return cButtons;
    }

    void unsetChoiseButtonsListener() {
        for (Button button : cButtons) {
            button.setOnClickListener(null);
        }
    }

    String getChoiseButtonsNumber() {
        StringBuilder result = new StringBuilder();
        for (Button button : cButtons) {
            result.append(button.getText().toString());
        }
        if (result.length() < 4)
            result = new StringBuilder("0000");
        return result.toString();
    }

    Buttons(HardActivity view) {
        //Кнопки помошники
        button0 = view.findViewById(R.id.hb0);
        button1 = view.findViewById(R.id.hb1);
        button2 = view.findViewById(R.id.hb2);
        button3 = view.findViewById(R.id.hb3);
        button4 = view.findViewById(R.id.hb4);
        button5 = view.findViewById(R.id.hb5);
        button6 = view.findViewById(R.id.hb6);
        button7 = view.findViewById(R.id.hb7);
        button8 = view.findViewById(R.id.hb8);
        button9 = view.findViewById(R.id.hb9);
        buttons = new Button[]{button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
        buttonColors = new String[]{"white", "white", "white", "white", "white", "white", "white", "white", "white", "white"};
        for (final Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonNumber = Integer.parseInt(button.getText().toString());
                    String color = buttonColors[buttonNumber];
                    if (color.equals("white")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        buttonColors[buttonNumber] = "red";
                    } else if (color.equals("red")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(0, 153, 205)));
                        buttonColors[buttonNumber] = "blue";
                    } else {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
                        buttonColors[buttonNumber] = "white";
                    }
                }
            });
        }
        //Кнопки для ввода данных
        cb1 = view.findViewById(R.id.cb1);
        cb2 = view.findViewById(R.id.cb4);
        cb3 = view.findViewById(R.id.cb3);
        cb4 = view.findViewById(R.id.cb2);
        cButtons = new Button[]{cb1, cb2, cb3, cb4};
        for (final Button button : cButtons) {
            button.setText("");
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    String buttonNumber = button.getText().toString();
                    if (buttonNumber.equals("")) button.setText("0");
                    else if (Integer.parseInt(buttonNumber) == 9) button.setText("");
                    else {
                        int bn = Integer.parseInt(buttonNumber);
                        bn++;
                        button.setText("" + bn);
                    }
                }
            });
        }
    }

    Buttons(TrainingActivity view) {
        //Кнопки помошники
        button0 = view.findViewById(R.id.hb0);
        button1 = view.findViewById(R.id.hb1);
        button2 = view.findViewById(R.id.hb2);
        button3 = view.findViewById(R.id.hb3);
        button4 = view.findViewById(R.id.hb4);
        button5 = view.findViewById(R.id.hb5);
        button6 = view.findViewById(R.id.hb6);
        button7 = view.findViewById(R.id.hb7);
        button8 = view.findViewById(R.id.hb8);
        button9 = view.findViewById(R.id.hb9);
        buttons = new Button[]{button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
        buttonColors = new String[]{"white", "white", "white", "white", "white", "white", "white", "white", "white", "white"};
        for (final Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonNumber = Integer.parseInt(button.getText().toString());
                    String color = buttonColors[buttonNumber];
                    if (color.equals("white")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        buttonColors[buttonNumber] = "red";
                    } else if (color.equals("red")) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(0, 153, 205)));
                        buttonColors[buttonNumber] = "blue";
                    } else {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(215, 215, 215)));
                        buttonColors[buttonNumber] = "white";
                    }
                }
            });
        }
    }

    String numberInput(String input, String number) {
        String output;
        if (input.length() < 4 && !input.contains(number))
            output = input + number;
        else if (input.length() == 9 && input.substring(7, 8).equals("3") && input.substring(8, 9).equals("B") && Integer.parseInt(number) > 0)
            output = input;
        else if (input.length() == 9 && input.substring(7, 8).equals("4") && input.substring(8, 9).equals("B") && Integer.parseInt(number) != 0)
            output = input;
        else if (input.length() == 9 && Integer.parseInt(input.substring(7, 8)) + Integer.parseInt(number) > 4)
            output = input;
        else if ((input.length() == 7 || input.length() == 9) && Integer.parseInt(number) < 5)
            output = input + number;
        else
            output = input;

        return output;
    }

    String del(String input) {
        String output;
        if (input.length() > 0 && (input.length() < 5 || input.length() > 7))
            output = input.substring(0, input.length() - 1);
        else
            output = input;

        return output;
    }

    String easyDel(String input) {
        String output;
        if (input.length() > 0 && input.length() < 5)
            output = input.substring(0, input.length() - 1);
        else
            output = input;
        return output;
    }

    String bc(String input, String sym) {
        String output;
        if ((input.length() == 8 || input.length() == 10) && !input.contains(sym)) {
            output = input + sym;
        } else
            output = input;

        return output;
    }
}
