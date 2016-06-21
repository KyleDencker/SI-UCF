package com.c4cheats.mathyfish.objects;

/**
 * Created by kyledencker on 6/16/16.
 */
public class Questions {

    int unit1, unit2, opperand, answer;

    public Questions(int opp) {
        opperand = opp;
        unit1 = (int)(Math.random()*11);
        unit2 = (int)(Math.random()*11);
        switch (opp){
            case 0:
                answer = unit1 + unit2;
                break;
            case 1:
                answer = unit1 - unit2;
                break;
            case 2:
                answer = unit1 * unit2;
                break;
        }
    }

    public String getQuestion() {
        String build = unit1+" ";
        switch (opperand) {
            case 0:
                build+="+ ";
                break;
            case 1:
                build+="- ";
                break;
            case 2:
                build+="X ";
                break;
        }
        build += unit2 + " = ?";
        return build;
    }



}
