package com.mygdx.lecture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import Entities.Enemy;
import Entities.Hole;
import Entities.PlayerKnight;

public class DungeonLecture extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	// Background and screen width/height
	Sprite background;
	Sprite background2;
	float w;
	float h;

	// Game entities
	PlayerKnight knight;
	Sprite spriteKnight;
	Hole blockedSquare;
	Sprite blocked;
	Rectangle blockedRect;
	ArrayList<Rectangle> edges;
	Rectangle door;

	//Score variables
	private int score;
	private String gameScore;
	BitmapFont scoreBitmap;

	Music music;

	// BFS variables
	boolean[][] isBlocked;
	int[][] playerDists;
	int knightGridX;
	int knightGridY;
	int EnemyGridX;
	int EnemyGridY;
	int dx[] = {0, 0, 1, -1};
	int dy[] = {1, -1, 0, 0};
	Enemy enemy;
	float time;

	@Override
	public void create () {

		// Make batch and get device width and height
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		// Set up Camera
		camera = new OrthographicCamera(w, h);
		camera.translate(w/2, h/2);
		camera.update();

		// Score instantiation
		score = 0;
		gameScore = "Your score: ";
		scoreBitmap = new BitmapFont();



		// Set up background
		background = new Sprite(new Texture("SingleScreenRoom1.png"));
		background.setSize(w, h);
		background2 = new Sprite(new Texture("SingleScreenRoom2.png"));
		background2.setSize(w, h);
		background2.setPosition(0, h);

		// Make a knight
		knight = new PlayerKnight(w/16, h/9, w/16, h/9);
		enemy = new Enemy(w/16 * 14, h/9 * 6, w/16, h/9);
		blockedSquare = new Hole(w/16 * 5, h/9 *3, w/16, h/9);
		door = new Rectangle(w/16 *7, h/9 * 7, w/16 *2, h/9 * 2);

		// blocked = new Sprite(new Texture("block.png:"));
		blockedRect = new Rectangle(w/16 *3, h/9 *4, w/16, h/9);

		edges = new ArrayList<Rectangle>();
		edges.add(new Rectangle(0, h/9 * 7, w, h/9 * 2));
		edges.add(new Rectangle(0, 0, w, h/9));
		edges.add(new Rectangle(0,0, w/16, h));
		edges.add(new Rectangle(w/16*15, 0, w/16, h));
		/*
		spriteKnight = new Sprite(new Texture("Knight.png"));
		spriteKnight.setPosition(0,0);
		spriteKnight.setSize(w/16, h/9);
        */

		//Set up music
		// music = Gdx.audio.newMusic(Gdx.files.internal("Medieval Melancholy.wav"));
		//music.setLooping(true);
		//music.play();


		/// Stuff for BFS begin ///
		knightGridX = 1;
		knightGridY = 1;
		EnemyGridX = 14;
		EnemyGridY = 6;

		time  = 0;

		isBlocked = new boolean[16][9];

		// Blocking bottom
		for(int i = 0; i<16; i++){
			isBlocked[i][0]=true;
		}
		// Blocking Top
		for(int i = 0; i<16; i++){
			isBlocked[i][8]=true;
		}
		// Blocking Top
		for(int i = 0; i<16; i++){
			isBlocked[i][7]=true;
		}
		// Blocking left
		for(int i = 0; i<9; i++){
			isBlocked[0][i]=true;
		}
		// Blocking right
		for(int i = 0; i<9; i++){
			isBlocked[15][i]=true;
		}

		// Block middle block
		for(int i= 0; i< 9; i++) {
			isBlocked[5][i] = true;
		}
		playerDists = new int[16][9];
		for(int i = 0; i<16; i++){
			for(int j = 0; j<9; j++){
				playerDists[i][j] = 100;
			}
		}
		/// Stuff for BFS end ///
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		// Draw stuff
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		background2.draw(batch);
		knight.draw(batch);
		enemy.draw(batch);
		blockedSquare.draw(batch);
		scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		scoreBitmap.draw(batch, gameScore, 0, h/9*8);
		// blocked.draw(batch);
		batch.end();

		//Archive Knight Position
		float oldx = knight.x;
		float oldy = knight.y;
		int oldGridX = knightGridX;
		int oldGridY = knightGridY;
		float oldEnemyX = enemy.x;
		float oldEnemyY = enemy.y;
		int oldEnemyGridX = EnemyGridX;
		int oldEnemyGridY = EnemyGridY;

		scoreBitmap.getData().setScale(2);

		// Handle Input
		// Keyboard
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            knight.moveLeft();
            knightGridX -= 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            knight.moveRight();
            score++;
            gameScore = "Your Score: "+score;
			knightGridX += 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            knight.moveUp();
            // music.pause();
			knightGridY += 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            knight.moveDown();
            // music.play();
			knightGridY -= 1;
		}



		//Wrapping
		/*
		if(knight.x  < 0 ){
			knight.updatePos(w-w/16, knight.y);
		}
		if(knight.x >= w){
			knight.updatePos(0, knight.y);
		}
		if(knight.y <0){
			knight.updatePos(knight.x, h-h/9);
		}
		if(knight.y >= h){
			knight.updatePos(knight.x, 0);
		}

		if(knight.hitBox.overlaps(blockedSquare.hitBox)){
			knight.updatePos(oldx, oldy);
		}
		*/

		// Transport knight to touched position.
		/*
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			knight.updatePos(touchPos.x, touchPos.y);
		}
		*/

		// Collision detection for the edges
		if(knight.hitBox.overlaps(door)){
			camera.translate(0f, h);
			knight.updatePos(w/16, h/9 + h);
			knightGridX = oldGridX;
			knightGridY = oldGridY;
		}
		else {
			for (int i = 0; i < edges.size(); i++) {
				if (knight.hitBox.overlaps(edges.get(i))) {
					knight.updatePos(oldx, oldy);
					knightGridX = oldGridX;
					knightGridY = oldGridY;
				}
			}
		}

		bfsUpdate(knightGridX, knightGridY);

		// Enemy Move
		time += Gdx.graphics.getDeltaTime();
		if(time >= 1) {
			time = 0;
			int crntDist = 100;
			Point minPoint = new Point(oldEnemyGridX, oldEnemyGridY);
			for (int i = 0; i < 4; i++) {
				int newX = EnemyGridX + dx[i];
				int newY = EnemyGridY + dy[i];

				if (inbounds(newX, newY) && !isBlocked[newX][newY]) {
					if (playerDists[newX][newY] < crntDist) {
						crntDist = playerDists[newX][newY];
						minPoint.x = newX;
						minPoint.y = newY;
					}
				}
			}

			EnemyGridX = minPoint.x;
			EnemyGridY = minPoint.y;

			enemy.updatePos(EnemyGridX * w / 16, EnemyGridY * h / 9);
		}


	}

	public void bfsUpdate(int x, int y){
		LinkedList<Point> queue = new LinkedList<Point>();
		boolean[][] visited = new boolean[16][9];

		queue.add(new Point(x, y));
		visited[x][y] = true;
		playerDists[x][y] = 0;
		int dist = 0;

		while(!queue.isEmpty()){
			Point crnt = queue.poll();

			for(int i = 0; i<4; i++){
				int newX = crnt.x + dx[i];
				int newY = crnt.y + dy[i];
				if(inbounds(newX, newY) && !visited[newX][newY] && !isBlocked[newX][newY]){
					queue.add(new Point(newX, newY));
					visited[newX][newY] = true;
					playerDists[newX][newY] = playerDists[crnt.x][crnt.y] + 1;
				}
			}
		}

	}

	public boolean inbounds(int x, int y){
		if(x>= 0 && x <16 & y>=0 && y < 9) return true;
		return false;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
