package com.gca.models;

import com.gca.screens.GameScreen;

public class Wizard extends Character {

	private static final int HEALTH = 5;
	
	public static final float SIZE = 100f/GameScreen.PIX_PER_UNIT;
	
	public Wizard(float x, float y, float width, float height) {
		super(x, y, width, height, HEALTH);
	}

}
