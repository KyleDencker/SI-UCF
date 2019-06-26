package BoardObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.Point;

public class DialogueBox {

    Sprite sprite;
    public Vector2[] positions;
    public String[] options;
    public Rectangle[] hitBoxes;

    float block_w = Gdx.graphics.getWidth()/16;
    float block_h = Gdx.graphics.getHeight()/9;



    public DialogueBox(float x, float y){
        sprite = new Sprite(new Texture("ChatRectangle.png"));
        sprite.setPosition(block_w *6, 0);
        sprite.setSize(block_w*3, block_h*2);

        positions = new Vector2[2];
        options = new String[2];
        hitBoxes = new Rectangle[2];

        positions[0] = new Vector2(block_w*6, block_h + block_h/3);
        hitBoxes[0] = new Rectangle(positions[0].x, positions[0].y, block_w, block_h/4);
        options[0] = "yes";

        positions[1] = new Vector2(block_w*6, block_h);
        hitBoxes[1] = new Rectangle(positions[1].x, positions[1].y, block_w, block_h/4);
        options[1] = "no";
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

}
