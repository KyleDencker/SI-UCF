package BoardObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import Manager.TextureManager;

public class Room {

    float x;
    float y;
    float width;
    float height;
    float block_w;
    float block_h;

    int gridx;
    int gridy;

    // Index 0: up Index 1: down Index 2: left Index 3: right
    public Room[] adjRooms;
    Sprite background;

    //Enemies
    public ArrayList<Enemy> enemies;

    // BFS Tools
    public int[][] distGrid;
    public boolean[][] isBlocked;
    int dx[] = {0, 0, 1, -1};
    int dy[] = {1, -1, 0, 0};

    // Hitboxes
    public Rectangle[] portals;
    public ArrayList<Rectangle> blockedAreas;

    // For use in random number generation
    public ArrayList<Point> availableSqrs;

    public Room(float x, float y, float w, float h, int gridx, int gridy){

        this.x = x;
        this.y = y;
        this.gridx = gridx;
        this.gridy = gridy;
        width = w;
        height = h;
        block_w = Gdx.graphics.getWidth()/16;
        block_h = Gdx.graphics.getHeight()/9;

        background = new Sprite(TextureManager.backgroundTexture);
        background.setSize(width, height);
        background.setPosition(this.x, this.y);

        distGrid = new int[9][9];
        isBlocked = new boolean[9][9];

        blockedAreas = new ArrayList<Rectangle>();
        blockedAreas.add(new Rectangle(x + 0, y + block_h*8, w, block_h));
        blockedAreas.add(new Rectangle(x + 0, y + 0, w, block_h));
        blockedAreas.add(new Rectangle(x + 0, y + 0, block_w, h));
        blockedAreas.add(new Rectangle(x + block_w * 8,  y + 0, block_w, h));

        portals = new Rectangle[4];
        portals[0] = new Rectangle(x + block_w * 4, y + block_h*8, block_w, h/9);
        portals[1] = new Rectangle(x + block_w * 4, y + 0, block_w, h/9);
        portals[2] = new Rectangle(x + 0, y + block_h*4, block_w, h/9);
        portals[3] = new Rectangle(x + block_w * 8, y + block_h*4, block_w, h/9);


        availableSqrs = new ArrayList<Point>();
        for(int i = 2; i <= 6; i++){
            for(int j  = 2; j<= 6; j++){
                availableSqrs.add(new Point(i,j));
            }
        }

        adjRooms = new Room[4];

        enemies = new ArrayList<Enemy>();
        for(int i = 0; i< 2; i++){
            int choose = (int)(Math.random() * ((availableSqrs.size() - 1) + 1));
            availableSqrs.get(choose);
            enemies.add(new Enemy(x + availableSqrs.get(choose).x*block_w, y + availableSqrs.get(choose).y*block_h, block_w, block_h));
            availableSqrs.remove(choose);
        }
        // for(int i = )
    }

    public void draw(SpriteBatch b){
        background.draw(b);
        for(int i = 0; i< enemies.size(); i++){
            enemies.get(i).draw(b);
        }
    }

    public void moveEnemy(Enemy enemy){

    }

    public void generateAdjRooms(HashMap<String, Room> usedRooms, ArrayList<Room> rooms){

        ArrayList<Room> addedRooms = new ArrayList<Room>();

        int nextGridX;
        int nextGridY;

        for(int i = 0; i < 4; i++){
            Room newRoom;
            if(i == 0){
                nextGridX = gridx;
                nextGridY = gridy + 1;
                String coords = makeCoordinateString(nextGridX, nextGridY);
                if(usedRooms.containsKey(coords)){
                    adjRooms[i] = usedRooms.get(coords);
                }
                else {
                    newRoom = new Room(x, y + height, width, height, nextGridX, nextGridY);
                    newRoom.adjRooms[1] = this;
                    adjRooms[i] = newRoom;
                    usedRooms.put(coords, newRoom);
                    rooms.add(newRoom);
                }
            }
            else if(i == 1){
                nextGridX = gridx;
                nextGridY = gridy - 1;
                String coords = makeCoordinateString(nextGridX, nextGridY);
                if(usedRooms.containsKey(coords)){
                    adjRooms[i] = usedRooms.get(coords);
                }
                else {
                    newRoom = new Room(x, y - height, width, height, nextGridX, nextGridY);
                    newRoom.adjRooms[0] = this;
                    adjRooms[i] = newRoom;
                    usedRooms.put(coords, newRoom);
                    rooms.add(newRoom);
                }
            }
            else if(i == 2){
                nextGridX = gridx - 1;
                nextGridY = gridy;
                String coords = makeCoordinateString(nextGridX, nextGridY);
                if(usedRooms.containsKey(coords)){
                    adjRooms[i] = usedRooms.get(coords);
                }
                else {
                    newRoom = new Room(x - width, y, width, height, nextGridX, nextGridY);
                    newRoom.adjRooms[3] = this;
                    adjRooms[i] = newRoom;
                    usedRooms.put(coords, newRoom);
                    rooms.add(newRoom);
                }
            }
            else if(i == 3){
                nextGridX = gridx + 1;
                nextGridY = gridy;
                String coords = makeCoordinateString(nextGridX, nextGridY);
                if(usedRooms.containsKey(coords)){
                    adjRooms[i] = usedRooms.get(coords);
                }
                else {
                    newRoom = new Room(x + width, y, width, height, nextGridX, nextGridY);
                    newRoom.adjRooms[2] = this;
                    adjRooms[i] = newRoom;
                    usedRooms.put(coords, newRoom);
                    rooms.add(newRoom);
                }
            }
        }
        return;
    }

    public String makeCoordinateString(int x, int y){
        String ret = x + "," + y;
        return ret;
    }
}
