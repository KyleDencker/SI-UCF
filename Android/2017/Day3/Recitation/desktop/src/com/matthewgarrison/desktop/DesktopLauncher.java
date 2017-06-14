package com.matthewgarrison.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.matthewgarrison.GameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameController(), config);
		config.height = 600;
		config.width = 1000;
		//config.fullscreen = true;
		config.title = "Kyle";
	}
}
