package com.makienkovs.bullsandcows;

import java.util.ArrayList;
import java.util.Random;

class Logic {
    private Random random = new Random();

    private ArrayList<String> fillArr() {
        ArrayList<String> aon = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String el;
            if (i < 10) el = "000" + i;
            else if (i < 100) el = "00" + i;
            else if (i < 1000) el = "0" + i;
            else el = "" + i;

            if (check(el)) {
                aon.add(el);
            }
        }
        return aon;
    }

    boolean check(String el){
        return !el.substring(1).contains(el.substring(0, 1)) && !el.substring(2).contains(el.substring(1, 2)) && !el.substring(3).contains(el.substring(2, 3));
    }

    private ArrayList<String> aon = fillArr();

    String getCompNumber(){
        return aon.get(random.nextInt(5040));
    }

    String[] filter(String el, String choice){
        int bull = 0;
        int cow = 0;
        String[] result = new String[2];

        for (int i = 0; i < el.length(); i++){
            if(el.substring(i, i+1).equals(choice.substring(i, i+1))){
                bull++;
                if (i == 0)
                    el = "b" + el.substring(1);
                else
                    el = el.substring(0, i) + "b" + el.substring(i+1);
            }
        }

        for (int i = 0; i < el.length(); i++)
            if(choice.contains(el.substring(i, i+1))) cow++;

        result[0] = Integer.toString(bull);
        result[1] = Integer.toString(cow);

        return result;
    }

    String getCompChoice() {
        return aon.get(random.nextInt(aon.size())) + " - ";
    }

    boolean validation() {
        return aon.size() == 0;
    }

    String takeManNumber(String output) {
        String bulls = null;
        String cows = null;

        if (output.indexOf("B") == 8){
            bulls = output.substring(7,8);
            cows = output.substring(9,10);
        }
        else if(output.indexOf("B") == 10){
            bulls = output.substring(9,10);
            cows = output.substring(7,8);
        }

        output = output.substring(0,4);

        for (int i = 0; i < aon.size(); i++){
            String[] elArr = filter(aon.get(i), output);
            if (!elArr[0].equals(bulls) || !elArr[1].equals(cows)){
                aon.remove(i);
                i--;
                if (aon.size() == 0) break;
            }
        }
        return bulls;
    }
}
