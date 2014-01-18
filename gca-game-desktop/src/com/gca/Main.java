package com.gca;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "gca-game";
		cfg.useGL20 = false;
		cfg.width = 720;
		cfg.height = 1280;
		
		new LwjglApplication(new GCAGame(), cfg);
	}
}
