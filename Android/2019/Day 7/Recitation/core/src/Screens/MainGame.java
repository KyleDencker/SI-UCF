package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.dungeon.GameController;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import BoardObjects.Enemy;
import BoardObjects.Overlay;
import BoardObjects.PlayerKnight;
import BoardObjects.Room;
import Manager.TextureManager;

public class MainGame implements Screen {

    SpriteBatch batch;
    OrthographicCamera camera;
    GameController myGame;

    PlayerKnight pknight;
    Room crntRoom;
    Overlay overlay;
    ArrayList<Room> rooms;
    HashMap<String, Room> usedRooms;

    boolean isChangingRoom;
    float nextKnightX;
    float nextKnightY;
    float nextCameraX;
    float nextCameraY;
    int portal;


    // BFS variables
    boolean[][] isBlocked;
    int[][] playerDists;
    int knightGridX;
    int knightGridY;
    int EnemyGridX;
    int EnemyGridY;
    int dx[] = {0, 0, 1, -1};
    int dy[] = {1, -1, 0, 0};
    float h;
    float w;
    float block_w;
    float block_h;
    Enemy enemy;
    float time;

    public MainGame(GameController g){
        myGame = g;
    }

    @Override
    public void show() {


        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        block_w = w/16;
        block_h = h/9;

        batch = new SpriteBatch();

        rooms  = new ArrayList<Room>();
        usedRooms = new HashMap<String, Room>();

        camera = new OrthographicCamera(w*5,h*5);
        //camera = new OrthographicCamera(w,h);
        camera.translate(w/2 - block_w * 3, h/2);
        camera.update();

        crntRoom = new Room(0, 0, block_w*9, block_h*9, 0, 0);
        rooms.add(crntRoom);
        crntRoom.generateAdjRooms(usedRooms, rooms);

        overlay = new Overlay(- block_w * 3, 0, block_w, block_h);

        pknight = new PlayerKnight(block_w, block_h, block_w, block_h);

        // Flag for room change
        isChangingRoom = false;
        portal = -1;

        /*

		/// Stuff for BFS begin ///
		knightGridX = 1;
		knightGridY = 1;
		EnemyGridX = 14;
		EnemyGridY = 6;

		time  = 0;

		isBlocked = new boolean[16][9];

		for(int i = 0; i<16; i++){
			isBlocked[i][0]=true;
		}
		for(int i = 0; i<16; i++){
			isBlocked[i][8]=true;
		}
		for(int i = 0; i<16; i++){
			isBlocked[i][7]=true;
		}
		for(int i = 0; i<9; i++){
			isBlocked[0][i]=true;
		}
		for(int i = 0; i<9; i++){
			isBlocked[15][i]=true;
		}

		isBlocked[4][3] = true;

		playerDists = new int[16][9];
		for(int i = 0; i<16; i++){
			for(int j = 0; j<9; j++){
				playerDists[i][j] = 100;
			}
		}
		*/
        /// Stuff for BFS end ///
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        crntRoom.draw(batch);

        for(int i = 0; i < 4; i++){
            crntRoom.adjRooms[i].draw(batch);
        }
        /*
        for(Room room: rooms){
            room.draw(batch);
        }
        */
        pknight.draw(batch);
        overlay.draw(batch);
        batch.end();

        if(isChangingRoom){

            if(pknight.x != nextKnightX || pknight.y != nextKnightY) {
                if (portal == 0) {
                    pknight.updatePos(pknight.x, pknight.y + 1);
                }
                if (portal == 1) {
                    pknight.updatePos(pknight.x, pknight.y - 1);
                }
                if (portal == 2) {
                    pknight.updatePos(pknight.x - 1, pknight.y);
                }
                if (portal == 3) {
                    pknight.updatePos(pknight.x + 1, pknight.y);
                }
            }
            if(camera.position.x != nextCameraX || camera.position.y != nextCameraY)
                moveCameraAndOverlay(portal, 1f);

            if(camera.position.x == nextCameraX && camera.position.y == nextCameraY
                && pknight.x == nextKnightX && pknight.y == nextKnightY){
                isChangingRoom = false;
            }
        }
        else {

            // Archive position
            float oldX = pknight.x;
            float oldY = pknight.y;

            // Handle Input
            // Keyboard
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                pknight.moveUp();
                knightGridY += 1;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                pknight.moveDown();
                knightGridY -= 1;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                //camera.translate(-w/16, 0, 0);
                pknight.moveLeft();
                knightGridX -= 1;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                pknight.moveRight();
                knightGridX += 1;
            }

            portal = checkPortalOverlap(pknight);

            // If portal != -1 Knight is standing on portal so move the camera and set the new room
            if (portal != -1) {

                isChangingRoom = true;

                if (portal == 0) {
                    nextKnightX = pknight.x;
                    nextKnightY = pknight.y + block_h * 2;
                    nextCameraX = camera.position.x;
                    nextCameraY = camera.position.y + block_h * 9;
                } else if (portal == 1) {
                    nextKnightX = pknight.x;
                    nextKnightY = pknight.y - block_h * 2;
                    nextCameraX = camera.position.x;
                    nextCameraY = camera.position.y - block_h * 9;
                } else if (portal == 2) {
                    nextKnightX = pknight.x - block_w * 2;
                    nextKnightY = pknight.y;
                    nextCameraX = camera.position.x - block_w * 9;
                    nextCameraY = camera.position.y;
                } else if (portal == 3) {
                    nextKnightX = pknight.x + block_w * 2;
                    nextKnightY = pknight.y;
                    nextCameraX = camera.position.x + block_w * 9;
                    nextCameraY = camera.position.y;
                }

                crntRoom = crntRoom.adjRooms[portal];
                crntRoom.generateAdjRooms(usedRooms, rooms);

            } else {
                for (int i = 0; i < crntRoom.blockedAreas.size(); i++) {
                    if (pknight.rect.overlaps(crntRoom.blockedAreas.get(i))) {
                        pknight.updatePos(oldX, oldY);
                    }
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

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


    public int checkPortalOverlap(PlayerKnight pknight){

        for(int i = 0; i < 4; i++){
            if(pknight.rect.overlaps(crntRoom.portals[i]))
                return i;
        }
        return -1;
    }

    public void moveCameraAndOverlay(int dir, float dist){
        if(dir == 0){
            camera.translate(0, dist);
            overlay.translate(0, dist);
        }
        else if(dir == 1){
            camera.translate(0, -dist);
            overlay.translate(0, -dist);
        }
        else if(dir == 2){
            camera.translate(-dist, 0);
            overlay.translate(-dist, 0);
        }
        else if(dir == 3){
            camera.translate(dist,0);
            overlay.translate(dist,0);
        }
    }
}
