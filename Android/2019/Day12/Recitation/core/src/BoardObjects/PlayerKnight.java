package BoardObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerKnight {
    public float WIDTH;
    public float HEIGHT;
    private static Texture texture;
    public Sprite sprite;
    private Sound stepSound = Gdx.audio.newSound(Gdx.files.internal("TileStuff\\stepsound.wav"));
    public float x, y;
    public Rectangle rect;
    public Rectangle chatRect;

    public PlayerKnight(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        WIDTH = w;
        HEIGHT = h;
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
        chatRect = new Rectangle(x+WIDTH, y, WIDTH, HEIGHT);
        texture = new Texture("TileStuff\\Knight.png");
        sprite = new Sprite(texture);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(this.x, this.y);
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
        chatRect.x = this.x + WIDTH;
        chatRect.y = this.y;
    }

    public void moveLeft(){
        sprite.translateX(-WIDTH);
        x = sprite.getX();
        rect.x = x;
        chatRect.x = this.x + WIDTH;
        stepSound.play();
    }

    public void moveRight(){
        sprite.translateX(WIDTH);
        x = sprite.getX();
        rect.x = x;
        chatRect.x = this.x + WIDTH;
        stepSound.play();
    }
    public void moveUp(){
        sprite.translateY(HEIGHT);
        y = sprite.getY();
        rect.y = y;
        chatRect.x = this.x + WIDTH;
        chatRect.y = this.y;
        stepSound.play();
    }
    public void moveDown(){
        sprite.translateY(-HEIGHT);
        y = sprite.getY();
        rect.y = y;
        chatRect.x = this.x + WIDTH;
        chatRect.y = this.y;
        stepSound.play();
    }
}
