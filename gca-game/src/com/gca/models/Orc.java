package com.gca.models;

import com.badlogic.gdx.math.Rectangle;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;


public class Orc extends Character implements Timeable {
	
	private static final float SHOOT_TIMER = 2f; //2 seconds per arrow
	
	private static final int HEALTH = 3;
	private static final float ORC_HEIGHT = 100f/GameScreen.PIX_PER_UNIT;
	private static final float ORC_WIDTH = 50f/GameScreen.PIX_PER_UNIT;
	
	private final Rectangle hitBox;
	
	private float timeSinceShoot;
	
	public Orc(float x, float y) {
		super(x, y, HEALTH);
		this.hitBox = new Rectangle();
		hitBox.width = ORC_WIDTH;
		hitBox.height = ORC_HEIGHT;
		timeSinceShoot = 0;
	}
	
	public Rectangle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}
	
	public boolean shootArrow() {
		if (timeSinceShoot >= SHOOT_TIMER) {
			timeSinceShoot = 0f;
			return true;
		}
		return false;
	}

	@Override
	public void addTime(float delta) {
		timeSinceShoot += delta;
	}
	
}
