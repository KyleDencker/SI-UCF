package edu.ucf.day1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.ucf.day1.GameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 500;
		config.height = 500;
		config.title = "My First Video Game in libGDX";

		new LwjglApplication(new GameController(), config);
	}
}
