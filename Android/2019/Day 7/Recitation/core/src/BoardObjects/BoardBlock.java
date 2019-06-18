package BoardObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class BoardBlock {
    public float WIDTH;
    public float HEIGHT;
    private static Texture texture;
    public Sprite sprite;

    float x, y;
    public Rectangle rect;

    public BoardBlock(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        WIDTH = w;
        HEIGHT = h;
        this.rect = new Rectangle(x, y, WIDTH, HEIGHT);
        texture = new Texture("TileStuff\\block.png");
        sprite = new Sprite(texture);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(this.x, this.y);
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }
}
