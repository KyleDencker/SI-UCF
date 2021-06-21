package com.fhsps.clicker.tools;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public static Texture[] NUMBERS;
    public static Texture[] BACKGROUND;
    public static Texture[] PIPE;
    public static Texture[][] BIRD;
    public static Texture ground, gameover, start;

    public static void loadNumbers(){
        NUMBERS = new Texture[10];
        for (int i=0; i<10; i++) {
           NUMBERS[i] = new Texture("sprites/" + i + ".png");
        }
    }

    public static void disposeNumbers() {
        for (int i=0; i<NUMBERS.length; i++) {
            if (NUMBERS[i] != null) {
                NUMBERS[i].dispose();
            }
        }
    }

    public static void loadSceen() {
        BACKGROUND = new Texture[2];
        BACKGROUND[0] = new Texture("sprites/background-day.png");
        BACKGROUND[1] = new Texture("sprites/background-night.png");

        PIPE = new Texture[2];
        PIPE[0] = new Texture("sprites/pipe-green.png");
        PIPE[1] = new Texture("sprites/pipe-red.png");

        BIRD = new Texture[3][4];
        BIRD[0][0] = new Texture("sprites/bluebird-downflap.png");
        BIRD[0][1] = new Texture("sprites/bluebird-midflap.png");
        BIRD[0][2] = new Texture("sprites/bluebird-upflap.png");
        BIRD[0][3] = new Texture("sprites/bluebird-midflap.png");


        BIRD[1][0] = new Texture("sprites/redbird-downflap.png");
        BIRD[1][1] = new Texture("sprites/redbird-midflap.png");
        BIRD[1][2] = new Texture("sprites/redbird-upflap.png");
        BIRD[1][3] = new Texture("sprites/redbird-midflap.png");



        BIRD[2][0] = new Texture("sprites/yellowbird-downflap.png");
        BIRD[2][1] = new Texture("sprites/yellowbird-midflap.png");
        BIRD[2][2] = new Texture("sprites/yellowbird-upflap.png");
        BIRD[2][3] = new Texture("sprites/yellowbird-midflap.png");


        ground = new Texture("sprites/base.png");
        gameover = new Texture("sprites/gameover.png");
        start = new Texture("sprites/message.png");
    }

    public static void disposeScreen() {
        start.dispose();
        gameover.dispose();
        ground.dispose();

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                BIRD[i][j].dispose();
            }
        }

        for (int i=0; i<2; i++) {
            BACKGROUND[i].dispose();
            PIPE[i].dispose();
        }
    }

}
