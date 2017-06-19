package com.matthewgarrison.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.matthewgarrison.GameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameController(), config);
		config.title = "Summer Institute";
		config.initialBackgroundColor = Color.BLACK;
		config.width = 1200;
		config.height = 675;
	}
}
