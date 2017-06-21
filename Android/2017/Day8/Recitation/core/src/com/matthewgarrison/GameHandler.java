package com.matthewgarrison;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.matthewgarrison.screens.MainGame;

public class GameHandler extends Game {
	public final static int SCREEN_WIDTH = 20, SCREEN_HEIGHT = 11;
	public Preferences prefs;

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("My preferences");
		this.setScreen(new MainGame(this));
	}

}