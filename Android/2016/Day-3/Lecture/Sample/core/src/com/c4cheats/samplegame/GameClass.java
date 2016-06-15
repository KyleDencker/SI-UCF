package com.c4cheats.samplegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.samplegame.com.c4cheats.samplegame.screens.MainGame;
import com.c4cheats.samplegame.com.c4cheats.samplegame.screens.Menu;

import java.util.ArrayList;

public class GameClass extends Game {



	@Override
	public void create () {
		this.setScreen(new Menu(this));
	}


}
