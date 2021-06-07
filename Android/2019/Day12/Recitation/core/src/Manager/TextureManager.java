package Manager;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public static Texture backgroundTexture;
    public static Texture knightTexture;
    public static Texture enemyTexture;
    public static Texture holeTexture;
    public static Texture overlayBackgroundLeft;
    public static Texture overlayBackgroundRight;
    public static Texture startButton;

    public static void loadTextures(){
        backgroundTexture = new Texture("TileStuff\\SingleScreen2.png");
        knightTexture = new Texture("TileStuff\\Knight.png");
        enemyTexture = new Texture("TileStuff\\Enemy.png");
        holeTexture = new Texture("TileStuff\\block.png");
        overlayBackgroundLeft = new Texture("TileStuff\\OverlayLeft.png");
        overlayBackgroundRight = new Texture("TileStuff\\OverlayRight.png");
        startButton = new Texture("TileStuff\\StartButton.png");

    }
}
