package com.miketriana.tankgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Disposable;
import com.miketriana.tankgame.objects.Tank;

public class Hud implements Disposable {
    OrthographicCamera camera;

    Sprite healthBarBg, healthBar, arrowButton, plus, minus;
    Circle leftButton, rightButton, upButton, downButton, plusButton, minusButton;
    BitmapFont font, respawnText;


    int score = 0;
    int enemyScore = 0;
    int respawnTime = 0;

    float leftEdge;
    float rightEdge;
    float topEdge;
    float bottomEdge;

    public Hud () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        healthBarBg = AssetManager.getSprite("Health bar bg");
        healthBarBg.setSize(128, 16);
        healthBar = AssetManager.getSprite("Health bar");
        healthBar.setSize(128, 16);

        arrowButton = AssetManager.getSprite("Arrow button");
        arrowButton.setSize(64, 64);
        arrowButton.setOrigin(32, 32);
        leftButton = new Circle(0, 0, 32);
        rightButton = new Circle(0, 0, 32);
        upButton = new Circle(0, 0, 32);
        downButton = new Circle(0, 0, 32);

        plus = AssetManager.getSprite("Plus button");
        plus.setSize(64, 64);
        plusButton = new Circle(0, 0, 32);
        minus = AssetManager.getSprite("Minus button");
        minus.setSize(64, 64);
        minusButton = new Circle(0, 0, 32);

        font = new BitmapFont();
        font.setColor(0.5f, 0.6f, 0.6f, 1);
        font.getData().setScale(2);
        respawnText = new BitmapFont();
        respawnText.setColor(0.5f, 0.6f, 0.6f, 1);
        respawnText.getData().setScale(4);
    }

    public void draw (SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        healthBarBg.draw(batch);
        healthBar.draw(batch);

        arrowButton.setCenter(leftButton.x, leftButton.y);
        arrowButton.setRotation(180);
        arrowButton.draw(batch);
        arrowButton.setCenter(rightButton.x, rightButton.y);
        arrowButton.setRotation(0);
        arrowButton.draw(batch);
        arrowButton.setCenter(upButton.x, upButton.y);
        arrowButton.setRotation(90);
        arrowButton.draw(batch);
        arrowButton.setCenter(downButton.x, downButton.y);
        arrowButton.setRotation(270);
        arrowButton.draw(batch);

        plus.setCenter(plusButton.x, plusButton.y);
        plus.draw(batch);
        minus.setCenter(minusButton.x, minusButton.y);
        minus.draw(batch);

        font.draw(batch, "Score: " + score + " Enemy: " + enemyScore, leftEdge + 8, topEdge - 8);
        if (respawnTime > 0) {
            String msg = "Respawn in " + respawnTime + " seconds";
            GlyphLayout gl = new GlyphLayout();
            gl.setText(respawnText, msg);
            respawnText.draw(batch, msg, camera.position.x - gl.width / 2, camera.position.y);
        }
    }

    public void update (Tank player, Tank enemy) {
        float percentHealth = player.getHealth() / 1000;
        healthBar.setColor(1 - percentHealth, percentHealth, 0, 1);
        healthBar.setSize(128 * percentHealth, 16);

        score = player.getScore();
        enemyScore = enemy.getScore();
        respawnTime = (int) (player.getRespawnTime() + 0.99f);
    }

    public void resize (float width, float height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        leftEdge = camera.position.x - camera.viewportWidth / 2;
        rightEdge = camera.position.x + camera.viewportWidth / 2;
        topEdge = camera.position.y + camera.viewportHeight / 2;
        bottomEdge = camera.position.y - camera.viewportHeight / 2;

        healthBarBg.setPosition(leftEdge + 16, bottomEdge + 16);
        healthBar.setPosition(leftEdge + 16, bottomEdge + 16);

        leftButton.setPosition(rightEdge - 192, bottomEdge + 128);
        rightButton.setPosition(rightEdge - 64, bottomEdge + 128);
        upButton.setPosition(rightEdge - 128, bottomEdge + 192);
        downButton.setPosition(rightEdge - 128, bottomEdge + 64);

        plusButton.setPosition(rightEdge - 336, bottomEdge + 64);
        minusButton.setPosition(rightEdge - 256, bottomEdge + 64);
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    public int checkInput (float x, float y) {
        if (leftButton.contains(x, y))
            return 0;
        if (rightButton.contains(x, y))
            return 1;
        if (upButton.contains(x, y))
            return 2;
        if (downButton.contains(x, y))
            return 3;
        if (plusButton.contains(x, y))
            return 4;
        if (minusButton.contains(x, y))
            return 5;

        return -1;
    }

    public OrthographicCamera getCamera () {
        return camera;
    }
}
