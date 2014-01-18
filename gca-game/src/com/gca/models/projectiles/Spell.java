package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;

public class Spell extends LineProjectile {

	public static final float LENGTH = 74f/GameScreen.PIX_PER_UNIT;
	public static final float SPELL_HEIGHT = 55f/GameScreen.PIX_PER_UNIT;
	public static final float SPELL_WIDTH = 94f/GameScreen.PIX_PER_UNIT;
	public static final float VELOCITY = 700f/GameScreen.PIX_PER_UNIT;
	
	public float angle;
	
	public Spell(float x, float y, float length, Vector2 velocity, float angle, int damage) {
		super(x, y, length, velocity, angle, damage);
		this.angle = angle;
	}

}
