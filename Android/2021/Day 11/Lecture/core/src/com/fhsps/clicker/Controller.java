package com.fhsps.clicker;

import com.badlogic.gdx.Game;
import com.fhsps.clicker.scenes.FlappyJack;
import com.fhsps.clicker.scenes.MainMenu;
import com.fhsps.clicker.scenes.PoolingExample;


public class Controller extends Game {

	@Override
	public void create() {
		this.setScreen(new FlappyJack(this));
	}
}
