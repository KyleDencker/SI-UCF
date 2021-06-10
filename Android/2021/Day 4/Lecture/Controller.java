package com.fhsps.clicker;

import com.badlogic.gdx.Game;
import com.fhsps.clicker.scenes.MainMenu;


public class Controller extends Game {

	@Override
	public void create() {
		this.setScreen(new MainMenu(this));
	}
}
