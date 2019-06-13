package Entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    public float x;
    public float y;
    float width;
    float height;
    Sprite sprite;
    Rectangle rect;

    public Enemy(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        sprite = new Sprite(new Texture("Enemy.png"));
        sprite.setSize(w, h);
        sprite.setPosition(x, y);
        rect = new Rectangle(x, y, w, h);
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

    public void updatePos(float x, float y){
        this.x = x;
        this.y = y;
        sprite.setPosition(this.x, this.y);
        rect.x = this.x;
        rect.y = this.y;
    }

    public void moveLeft(){
        sprite.translateX(-width);
        x = sprite.getX();
        rect.x = x;
        //stepSound.play();
    }

    public void moveRight(){
        sprite.translateX(width);
        x = sprite.getX();
        rect.x = x;
        //stepSound.play();
    }
    public void moveUp(){
        sprite.translateY(width);
        y = sprite.getY();
        rect.y = y;
        //stepSound.play();
    }
    public void moveDown(){
        sprite.translateY(width);
        y = sprite.getY();
        rect.y = y;
        //stepSound.play();
    }
}