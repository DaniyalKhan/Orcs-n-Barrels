package com.gca.models;

import com.badlogic.gdx.math.Rectangle;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;

public class Obstacle extends GameModel implements Timeable {

	private static final int OBSTACLE_DAMAGE = 1;
	
	private static float OBSTACLE_WIDTH = 100f/GameScreen.PIX_PER_UNIT;
	private static float OBSTACLE_HEIGHT = 100f/GameScreen.PIX_PER_UNIT;
	
	private static float MOVE_SPEED = -100f/GameScreen.PIX_PER_UNIT;
	
	private final Rectangle hitBox;
	
	public Obstacle(float x, float y) {
		super(x, y);
		hitBox = new Rectangle(x, y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
	}
	
	public Rectangle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}

	@Override
	public void addTime(float delta) {
		position.y += MOVE_SPEED * delta;
	}

	public int getDamage() {
		return OBSTACLE_DAMAGE;
	}

	
	
}
