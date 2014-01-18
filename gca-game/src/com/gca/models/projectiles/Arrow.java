package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;

public class Arrow extends LineProjectile {
	
	public static final float LENGTH = 74f/GameScreen.PIX_PER_UNIT;
	public static final float ARROW_HEIGHT = 55f/GameScreen.PIX_PER_UNIT;
	public static final float ARROW_WIDTH = 94f/GameScreen.PIX_PER_UNIT;
	public static final float VELOCITY = 700f/GameScreen.PIX_PER_UNIT;
	
	public float angle;
	
	public Arrow(float x, float y, Vector2 velocity, float angle, int damage) {
		super(x, y, LENGTH, velocity, angle, damage);
		this.angle = angle;
	}

	
}
