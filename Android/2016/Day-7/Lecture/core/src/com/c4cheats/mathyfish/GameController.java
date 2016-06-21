package com.c4cheats.mathyfish;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.mathyfish.scenes.MainMenu;
import com.c4cheats.mathyfish.scenes.Splash;

public class GameController extends Game {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 675;

	public boolean onScreenControls;

	public GameController(boolean onScreen) {
		this.onScreenControls = onScreen;
	}

	public void create () {
		this.setScreen(new MainMenu(this));
	}


}
