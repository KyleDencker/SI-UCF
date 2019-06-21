package com.mygdx.golf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	ArrayList<LineSeg> course;
	ShapeRenderer shape;
	Ball ball;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(800,800);
		camera.translate(400, 400);
		camera.update();

		shape = new ShapeRenderer();

		ball = new Ball(50, 250);


		course = new ArrayList<LineSeg>();
		course.add(new LineSeg(new Vector2(000, 200), new Vector2(400,200)));
		course.add(new LineSeg(new Vector2(400, 200), new Vector2(600,400)));
		course.add(new LineSeg(new Vector2(600, 400), new Vector2(800,400)));
		course.add(new LineSeg(new Vector2(800, 400), new Vector2(800,600)));
		course.add(new LineSeg(new Vector2(800, 600), new Vector2(400,600)));
		course.add(new LineSeg(new Vector2(400, 600), new Vector2(400,400)));
		course.add(new LineSeg(new Vector2(400, 400), new Vector2(000,400)));
		course.add(new LineSeg(new Vector2(000, 400), new Vector2(000,200)));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.setColor(1, 1, 1, 1);
		// shape.line(1200, 400, 1400, 1000);
		for(LineSeg seg : course){
			shape.line(seg.start.x, seg.start.y, seg.end.x, seg.end.y);
		}
		shape.end();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		ball.draw(batch);
		batch.end();


		ball.update(Gdx.graphics.getDeltaTime());

		for(LineSeg seg : course){
			System.out.println("X: " + ball.circle.x+ " Y: " + ball.circle.y);
			if(Intersector.intersectSegmentCircle(seg.start, seg.end, new Vector2(ball.position.x, ball.position.y),100)){
				ball.reflect(seg.orthogonalVector());
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
