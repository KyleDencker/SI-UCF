package com.c4cheats.mathyfish.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.c4cheats.mathyfish.GameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mathy Fish - Desktop Test Project";
		config.width = 400;
		config.height = 675;
		new LwjglApplication(new GameController(true), config);
	}
}
