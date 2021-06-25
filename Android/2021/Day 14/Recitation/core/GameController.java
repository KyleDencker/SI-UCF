package com.miketriana.tankgame;

import com.badlogic.gdx.Game;
import com.miketriana.tankgame.screens.GameScreen;
import com.miketriana.tankgame.screens.LoseScreen;
import com.miketriana.tankgame.screens.TitleScreen;
import com.miketriana.tankgame.screens.WinScreen;
import com.miketriana.tankgame.utils.AssetManager;

public class GameController extends Game {
	public GameScreen gameScreen;
	public TitleScreen titleScreen;
	public WinScreen winScreen;
	public LoseScreen loseScreen;

	@Override
	public void create() {
		AssetManager.loadSprites();

		gameScreen = new GameScreen(this);
		titleScreen = new TitleScreen(this);
		winScreen = new WinScreen(this);
		loseScreen = new LoseScreen(this);

		setScreen(titleScreen);
	}

	@Override
	public void dispose () {
		AssetManager.disposeAssets();
	}
}
