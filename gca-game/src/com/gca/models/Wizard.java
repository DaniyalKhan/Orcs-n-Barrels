package com.gca.models;

import com.badlogic.gdx.math.Circle;
import com.gca.screens.GameScreen;

public class Wizard extends Character {

	private static final int HEALTH = 5;
	public static final float SIZE = 100f/GameScreen.PIX_PER_UNIT;
	
	private static final float RADIUS = (float) (Math.sqrt(2) * SIZE)/2f;
	
	private final Circle hitBox;
	
	public Wizard(float x, float y, float width, float height) {
		super(x, y, HEALTH);
		hitBox = new Circle();
		hitBox.radius = RADIUS;
	}
	
	public Circle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}

}
