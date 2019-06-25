package com.mygdx.golf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameController extends ApplicationAdapter implements ApplicationListener, InputProcessor {
	SpriteBatch batch;
	OrthographicCamera camera;
	ArrayList<LineSeg> course;
	ShapeRenderer shape;
	Ball ball;
	Hole hole;

	Boolean first;
	Boolean last;

	Vector2 firstTouch = new Vector2();
	Vector2 lastTouch = new Vector2();
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(800,800);
		camera.translate(400, 400);
		camera.update();

		shape = new ShapeRenderer();

		ball = new Ball(50, 250);
		hole = new Hole(750, 500);

		first  = false;
		last = false;

		course = new ArrayList<LineSeg>();
		course.add(new LineSeg(new Vector2(000, 200), new Vector2(400,200)));
		course.add(new LineSeg(new Vector2(400, 200), new Vector2(600,400)));
		course.add(new LineSeg(new Vector2(600, 400), new Vector2(800,400)));
		course.add(new LineSeg(new Vector2(800, 400), new Vector2(800,600)));
		course.add(new LineSeg(new Vector2(800, 600), new Vector2(400,600)));
		course.add(new LineSeg(new Vector2(400, 600), new Vector2(400,400)));
		course.add(new LineSeg(new Vector2(400, 400), new Vector2(000,400)));
		course.add(new LineSeg(new Vector2(000, 400), new Vector2(000,200)));

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.setColor(1, 1, 1, 1);
		shape.line(1200, 400, 1400, 1000);
		for(LineSeg seg : course){
			shape.line(seg.start.x, seg.start.y, seg.end.x, seg.end.y);
		}
		shape.end();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		ball.draw(batch);
		hole.draw(batch);
		batch.end();


		if(first && last){
			first = false;
			last = false;

			Vector3 inputFirst = new Vector3(firstTouch.x, firstTouch.y, 0);
			camera.unproject(inputFirst);

			Vector3 inputLast = new Vector3(lastTouch.x, lastTouch.y, 0);
			camera.unproject(inputLast);

			Vector2 delta = new Vector2(inputLast.x - inputFirst.x, inputLast.y - inputFirst.y);
			float dist = (float)Math.sqrt(delta.x * delta.x + delta.y * delta.y);

			delta.x = - delta.x;
			delta.y = - delta.y;
			ball.unit = delta.nor();
			ball.speed = dist;
		}

		ball.update(Gdx.graphics.getDeltaTime());

		if(ball.circle.overlaps(hole.circle)){
			ball.reset();
		}

		for(LineSeg seg : course){
			System.out.println("X: " + ball.circle.x+ " Y: " + ball.circle.y);
			if(Intersector.intersectSegmentCircle(seg.start, seg.end, new Vector2(ball.position.x, ball.position.y),50)){
				ball.reflect(seg.normalVector());
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		firstTouch.set(screenX,screenY);
		first = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		lastTouch.set(screenX, screenY);
		last =  true;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
