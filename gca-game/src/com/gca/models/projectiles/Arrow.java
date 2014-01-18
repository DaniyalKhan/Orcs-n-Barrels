package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;

public class Arrow extends LineProjectile {
	
	public static final float LENGTH = 94f/GameScreen.PIX_PER_UNIT;
	public static final float ARROW_HEIGHT = 55f/GameScreen.PIX_PER_UNIT;
	public static final float ARROW_WIDTH = 94f/GameScreen.PIX_PER_UNIT;
	private static final int ARROW_DAMAGE = 1;
	public static final float VELOCITY = 150f/GameScreen.PIX_PER_UNIT;
	
	public float angle;
	
	public Arrow(float x, float y, Vector2 velocity, float angle) {
		super(x, y, LENGTH, velocity, angle, ARROW_DAMAGE);
		this.angle = angle;
	}

	
}
