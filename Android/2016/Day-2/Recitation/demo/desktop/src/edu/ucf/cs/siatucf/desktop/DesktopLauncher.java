package edu.ucf.cs.siatucf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.ucf.cs.siatucf.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 750;
		config.height = 750;
		config.title = "Hi class!";
		new LwjglApplication(new MyGdxGame(), config);
	}
}
