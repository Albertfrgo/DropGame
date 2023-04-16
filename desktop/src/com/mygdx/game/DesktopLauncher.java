package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
/*import com.mygdx.game.MyGdxGame;*/
import com.mygdx.game.Drop;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("DropGame");
		// Establim la mida de finestra a 800, 480, he establert que no es pugui canviar la mida
		config.setWindowedMode(800, 480);
		config.useVsync(true);
		config.setResizable(false);
		config.setForegroundFPS(60);
		/*new Lwjgl3Application(new MyGdxGame(), config);*/
		new Lwjgl3Application(new Drop(), config);
	}
}
