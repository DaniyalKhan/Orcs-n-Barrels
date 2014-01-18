package com.gca.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;


public class Orc extends Character implements Timeable {
	
	private static final float SHOOT_TIMER = 2f; //2 seconds per arrow
	
	private static final int HEALTH = 3;
	private static final float ORC_WIDTH = 50f/GameScreen.PIX_PER_UNIT;
	private static final float ORC_HEIGHT = 100f/GameScreen.PIX_PER_UNIT;
	private static final float MOVE_SPEED = 125f/GameScreen.PIX_PER_UNIT;
	
	private final Rectangle hitBox;
	
	private float timeSinceShoot;
	
	private final Vector2 destination;
	
	public Orc(float x, float y) {
		super(x, y, HEALTH);
		this.hitBox = new Rectangle(x, y, ORC_WIDTH, ORC_HEIGHT);
		this.timeSinceShoot = 0;
		this.destination = new Vector2();
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
		float deltaX = destination.x - position.x;
		if (deltaX > 0) {
			position.x += delta * MOVE_SPEED;
			if (destination.x - position.x < 0) position.x = destination.x;
		} else if (deltaX < 0) {
			position.x -= delta * MOVE_SPEED;
			if (destination.x - position.x > 0) position.x = destination.x;
		}
	}
	
}
