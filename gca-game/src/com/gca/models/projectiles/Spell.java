package com.gca.models.projectiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;

public class Spell extends LineProjectile {

	public static final float LENGTH = 74f/GameScreen.PIX_PER_UNIT;
	public static final float FIRE_HEIGHT = 108f/GameScreen.PIX_PER_UNIT;
	public static final float FIRE_WIDTH = 243f/GameScreen.PIX_PER_UNIT;
	public static final float VELOCITY = 700f/GameScreen.PIX_PER_UNIT;
	
	public float angle;
	
	public final int type;
	
	private final Rectangle hitBox; 
	
	public Spell(float x, float y, float length, Vector2 velocity, float angle, int damage, int type) {
		super(x, y, length, velocity, angle, damage);
		this.angle = angle;
		this.type = type;
		this.hitBox = new Rectangle();
		hitBox.width = FIRE_WIDTH;
		hitBox.height = FIRE_HEIGHT;
	}

	public Rectangle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}
	
}
