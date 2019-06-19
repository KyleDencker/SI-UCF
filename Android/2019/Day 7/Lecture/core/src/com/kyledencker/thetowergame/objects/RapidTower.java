package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.math.Vector2;
import com.kyledencker.thetowergame.screens.MainGame;

import java.util.ArrayList;

public class RapidTower extends Shooter {

    public RapidTower(int x, int y) {
        super(x, y, "rapid");
    }

    public void update(float deltaTime, ArrayList<Player> playerList) {
        setLastFire(getLastFire()+deltaTime);
        if (getLastFire() >= getFireRate()) {

            for (Player p : playerList) {
                float deltaX = p.getPosition().x - this.getHitBox().x;
                float deltaY = p.getPosition().y - this.getHitBox().y;
                double dist = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
                if (dist <= 200) {

                    // Up, Down, Left right now in that order
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 + 1), (int)(getHitBox().y+getHitBox().height/2))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 - 1), (int)(getHitBox().y+getHitBox().height/2))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2-1))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2+1))));


                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 + 1), (int)(getHitBox().y+getHitBox().height/2 + 1))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 - 1), (int)(getHitBox().y+getHitBox().height/2 - 1))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 + 1), (int)(getHitBox().y+getHitBox().height/2-1))));
                    MainGame.bulletList.add(new Bullet((int)(getHitBox().x+getHitBox().width/2), (int)(getHitBox().y+getHitBox().height/2),
                            new Vector2((int)(getHitBox().x+getHitBox().width/2 - 1), (int)(getHitBox().y+getHitBox().height/2+1))));
                    setLastFire(0);
                }

            }
        }
    }
}
