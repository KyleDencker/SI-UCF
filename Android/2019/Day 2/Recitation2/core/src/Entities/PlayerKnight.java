package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerKnight {

    //Position
    public float x;
    public float y;

    //Size
    float width;
    float height;

    //Sprite
    Sprite sprite;

    //Sound
    private Sound stepSound = Gdx.audio.newSound(Gdx.files.internal("stepsound.wav"));

    // hit box
    public Rectangle hitBox;

    // Make constructor
    public PlayerKnight(float x, float y, float w, float h){

        // Set initial position and height and width of the knight
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        // Replicate settings for the sprite representations
        sprite = new Sprite(new Texture("Knight.png"));
        sprite.setPosition(x, y);
        sprite.setSize(w, h);

        hitBox = new Rectangle(this.x, this.y, width, height);
        // Set sound
    }

    // Draw the sprite
    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

    // Update Position
    public void updatePos(float x, float y){
        this.x = x;
        this.y = y;

        sprite.setPosition(x, y);
        hitBox.x = x;
        hitBox.y = y;
    }

    //Move left
    public void moveLeft(){
        sprite.translateX(-width);
        x = sprite.getX();
        y = sprite.getY();
        hitBox.x = x;
        hitBox.y = y;
        // Play a sound
        stepSound.play();
    }
    //Move Right
    public void moveRight(){
        sprite.translateX(width);
        x = sprite.getX();
        y = sprite.getY();
        hitBox.x = x;
        hitBox.y = y;
    }

    //Move Up
    public void moveUp(){
        sprite.translateY(height);
        x = sprite.getX();
        y = sprite.getY();
        hitBox.x = x;
        hitBox.y = y;
    }

    //Move down
    public void moveDown(){
        sprite.translateY(-height);
        x = sprite.getX();
        y = sprite.getY();
        hitBox.x = x;
        hitBox.y = y;
    }
}
