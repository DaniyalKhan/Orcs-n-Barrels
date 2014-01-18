package com.gca.models.projectiles;

import com.gca.screens.GameScreen;

public class Arrow extends LineProjectile {

	private static final float LENGTH = 70f/GameScreen.PIX_PER_UNIT;
	private static final int ARROW_DAMAGE = 1;
	private static final float VELOCITY = 150f/GameScreen.PIX_PER_UNIT;
	
	public Arrow(float x, float y, float angle, int damage) {
		super(x, y, LENGTH, VELOCITY, angle, ARROW_DAMAGE);
	}
	
}
