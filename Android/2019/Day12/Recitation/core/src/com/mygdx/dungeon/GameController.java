package com.mygdx.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.util.LinkedList;

import BoardObjects.Enemy;
import BoardObjects.Overlay;
import BoardObjects.Room;
import Manager.TextureManager;
import Screens.MainMenu;

public class GameController extends Game {

	@Override
	public void create () {
		TextureManager.loadTextures();
		setScreen(new MainMenu(this));
	}

}
