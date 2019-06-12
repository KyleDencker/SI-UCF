package Entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Hole {
    float x;
    float y;
    float width;
    float height;
    Sprite sprite;

    public Rectangle hitBox;

    public Hole(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        sprite = new Sprite(new Texture("block.png"));
        sprite.setSize(w, h);
        sprite.setPosition(this.x,this.y);
        hitBox = new Rectangle(this.x, this.y, w, h);
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }
}
