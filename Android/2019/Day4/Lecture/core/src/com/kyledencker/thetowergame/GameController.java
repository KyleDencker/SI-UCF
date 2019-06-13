package com.kyledencker.thetowergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyledencker.thetowergame.manager.TextureManager;
import com.kyledencker.thetowergame.objects.Player;
import com.kyledencker.thetowergame.screens.MainGame;
import com.kyledencker.thetowergame.screens.MainMenu;

public class GameController extends Game {

	@Override
	public void create() {
		TextureManager.loadUnits();
		setScreen(new MainMenu(this));
	}
}
