package com.c4cheats.easycontroller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.easycontroller.controllers.ControllerController;

import java.util.ArrayList;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    OrthographicCamera camera;
    float x=0, y=0, dx=0, dy=0;
    ArrayList<Vector2> points =  new ArrayList<Vector2>();
    ArrayList<Vector2> sizes  =  new ArrayList<Vector2>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1200);

        Controllers.addListener(new ControllerController());

        if (Controllers.getControllers().size > 0) {
            System.out.println("We have controllers");
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        for (int i=0; i<points.size(); i++) {
            batch.draw(img, points.get(i).x, points.get(i).y, sizes.get(i).x, sizes.get(i).y);
        }

		batch.draw(img, x, y);

		batch.end();

        if (Math.abs(ControllerController.axisValue[0]) > .2) {
            x += ControllerController.axisValue[0]*10;
        }
        if (Math.abs(ControllerController.axisValue[1]) > .2) {
            y -= ControllerController.axisValue[1] * 10;
        }

        if (ControllerController.buttonPressed[0]) {
            points.add(new Vector2(x, y));
            sizes.add(new Vector2(500, 500));
        }

        if (ControllerController.buttonPressed[1]) {
            points.add(new Vector2(x, y));
            sizes.add(new Vector2(200, 200));
        }
        if (ControllerController.buttonPressed[2]) {
            points.add(new Vector2(x, y));
            sizes.add(new Vector2(75, 75));
        }
        if (ControllerController.buttonPressed[3]) {
            points.add(new Vector2(x, y));
            sizes.add(new Vector2(50, 50));
        }
        if (ControllerController.buttonPressed[4]) {
            points.add(new Vector2(x, y));
            sizes.add(new Vector2(25, 25));
        }
        System.out.println(points.size());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


}
