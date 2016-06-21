package edu.ucf.cs.firstgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Scanner;

public class first extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture [] layers;

	OrthographicCamera camera;
	Sprite ball;
	Texture BG;

	int groundLevel = 220;

	float x = 0;
	float y = 400f;

	double vx = 0;
	double vy = .5;

	float sx = 400f;
	float sy = 400f;

	double svx = 0;
	double svy = .5;



	double g = -1;

	ArrayList<ParticleEffect> particles;
	ParticleEffect pe;
	int walkCounter = 0;
	int walkTemp;
	int walkMax = 4;

	boolean facingLeft;

	TextureRegion [] skullyL;

	Rectangle skulBox;
	Rectangle soccer;



	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		batch = new SpriteBatch();

		img = new Texture("soccerball.png");
		BG = new Texture("field.jpg");

		Texture t = new Texture("skeletona.png");
		skullyL = TextureRegion.split(t, 32,64)[0];
		for(int e = 0; e < 4; e++) {
			skullyL[e].flip(true,false);
		}
		layers = new Texture[8];

		particles = new ArrayList<ParticleEffect>();
		pe = new ParticleEffect();
		pe.load(Gdx.files.internal("cursor.pe"), Gdx.files.internal(""));
		for(int i = 1; i <= 8; i++) {
			layers[i-1] = new Texture("world/layer_0" + i + ".png");
		}

		ball = new Sprite(img);
		ball.setSize(32,32);
		skulBox = new Rectangle(x,y, 32,64);
		soccer = new Rectangle(x,y, 32,32);
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		camera.position.x = (float) x;
		camera.position.y = (float) y;
		camera.update();

		batch.begin();

		updateKeys();		// checks to see if WASD keys are pressed
		addGravity();
		drawBackground();
		updateWalk();
		updateBall();
		drawBall();
		drawParticles();


		batch.end();
	}

	public void drawParticles() {
		for(int i = particles.size()-1; i >= 0; i--) {
			particles.get(i).update(Gdx.graphics.getDeltaTime());
			particles.get(i).draw(batch);

			if(particles.get(i).isComplete()) {
				particles.get(i).dispose();
				particles.remove(i);
			}

		}
	}
	public void drawBackground() {
		for(int i = 7; i >= 0; i--) {
			if(i == 0) {
				batch.draw(layers[i], 0, 0);
				continue;
			}
			batch.draw(layers[i], (int)(-x/((i+20))), 0);
		}
	}

	public void addParticleEffect() {
		ParticleEffect temp = new ParticleEffect();
		temp.load(Gdx.files.internal("SplashEffect.pe"), Gdx.files.internal(""));
		temp.setPosition((float) x,(float)y);
		particles.add(temp);
		temp.start();
	}

	public void updateBall() {
		soccer.set(sx,sy,32,32);

		if(soccer.overlaps(skulBox)) {
			addParticleEffect();
			svy = 10;
			if(sx > x) {
				svx = 10;
			}
			else {
				svx = -10;
			}
		}

		svy += g;
		sx += svx;
		sy += svy;

		if(sy < groundLevel) {
			sy = groundLevel;
			svy *= -.7;	//bounces ball off of ground and slowly reduces speed of the ball to 0
		}
		if(sy == groundLevel) {
			svx *= .6; //add friction to x component of velocity if the ball is touching the ground.
		}
	}

	public void drawBall() {
		ball.setPosition((float)sx,(float)sy);
		ball.draw(batch);
	}

	public void updateWalk() {
		batch.draw(skullyL[walkCounter % walkMax], x, y);
		skulBox.set(x,y,32,64);
		if(Math.abs(vx) > 0.2) {
			if(vx > 0 && facingLeft) {
				for(int e = 0; e < 4; e++) {
					skullyL[e].flip(true,false);
				}
				facingLeft = false;
			}
			else if(vx < 0 && !facingLeft){
				for(int e = 0; e < 4; e++) {
					skullyL[e].flip(true,false);
				}
				facingLeft = true;
			}


			if(walkTemp % 10 == 0) {
				walkCounter++;
			}
			walkTemp++;
		}
		else {
			walkTemp = 0;
			walkCounter = 0;
		}
	}
	public void addGravity() {
		//adds gravity effect to ball and bounds the ball to ground
		vy += g;
		x += vx;
		y += vy;

		if(y < groundLevel) {
			y = groundLevel;
			vy *= -.7;	//bounces ball off of ground and slowly reduces speed of the ball to 0
		}
		if(y == groundLevel) {
			vx *= .6; //add friction to x component of velocity if the ball is touching the ground.
		}
	}

	public void updateKeys() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			ball.setColor((float)Math.random(),(float)Math.random(),(float)Math.random(),1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			vy = 10;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			y--;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			vx = -5;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			vx = 5;
		}
	}
}
