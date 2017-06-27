package com.matthewgarrison;

import com.badlogic.gdx.Game;

public class GameHandler extends Game {
	public final static int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 480, SQUARE_DIMENSION = 40;

	public GameHandler() {
	}

	public void create () {
		this.setScreen(new MainScreen(this));
	}

}