package com.c4cheats.spacegame;

import com.badlogic.gdx.Game;

import com.c4cheats.spacegame.scenes.MainGame;
import com.c4cheats.spacegame.scenes.MainMenu;


/*
Setup Instructions

https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

 */

public class  GameController extends Game {

	GameController myGame;

	public void create(){
		myGame = this;
		myGame.setScreen(new MainMenu(myGame));
	}



}
