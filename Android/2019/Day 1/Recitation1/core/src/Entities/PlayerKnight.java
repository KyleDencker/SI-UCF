package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerKnight {

    //Position
    float x;
    float y;

    //Size
    float width;
    float height;

    //Sprite
    Sprite sprite;

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
    }

    //Move left
    public void moveLeft(){
        // Play a sound
    }

    //Move Right

    //Move Up

    //Move down
}
