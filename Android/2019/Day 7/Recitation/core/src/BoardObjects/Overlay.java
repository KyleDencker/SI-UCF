package BoardObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Manager.TextureManager;

public class Overlay {
    float x;
    float y;
    float block_width;
    float block_height;

    int score;

    Sprite backgroundLeft;
    Sprite backgroundRight;

    // To do: controls

    public Overlay(float x, float y, float block_width, float block_height){
        this.x = x;
        this.y = y;
        this.block_width = block_width;
        this.block_height = block_height;

        backgroundLeft = new Sprite(TextureManager.overlayBackgroundLeft);
        backgroundLeft.setSize(block_width * 3, block_height * 9);
        backgroundLeft.setPosition(x, y);

        backgroundRight = new Sprite(TextureManager.overlayBackgroundRight);
        backgroundRight.setSize(block_width*4, block_height*9);
        backgroundRight.setPosition(x + block_width * 12, y);
    }

    public void translate(float distx, float disty){
        x = x + distx;
        y = y + disty;
        backgroundLeft.setPosition(x,y);
        backgroundRight.setPosition(x + block_width * 12, y);
    }

    public void draw(SpriteBatch b){
        backgroundRight.draw(b);
        backgroundLeft.draw(b);
    }

}
