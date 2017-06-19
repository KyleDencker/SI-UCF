package com.matthewgarrison;

import com.badlogic.gdx.Game;
import com.matthewgarrison.screens.MainGame;

public class GameController extends Game {
	public final static int SCREEN_WIDTH = 20, SCREEN_HEIGHT = 11;

	@Override
	public void create () {
		this.setScreen(new MainGame(this));
	}

}