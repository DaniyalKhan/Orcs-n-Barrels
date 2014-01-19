package com.gca.models;

import com.badlogic.gdx.math.Rectangle;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;

public class Obstacle extends GameModel implements Timeable {

	private static final int OBSTACLE_DAMAGE = 1;
	
	public static float LOG_WIDTH = 125f/GameScreen.PIX_PER_UNIT;
	public static float LOG_HEIGHT = 126f/GameScreen.PIX_PER_UNIT;
	public static float FISH_WIDTH = 110f/GameScreen.PIX_PER_UNIT;
	public static float FISH_HEIGHT = 77f/GameScreen.PIX_PER_UNIT;
	public static float ROCK_WIDTH = 154f/GameScreen.PIX_PER_UNIT;
	public static float ROCK_HEIGHT = 151f/GameScreen.PIX_PER_UNIT;
	public static float SHARK_WIDTH = 92f/GameScreen.PIX_PER_UNIT;
	public static float SHARK_HEIGHT = 109f/GameScreen.PIX_PER_UNIT;
	public static float OCTOPUS_WIDTH = 123f/GameScreen.PIX_PER_UNIT;
	public static float OCTOPUS_HEIGHT = 220f/GameScreen.PIX_PER_UNIT;
	
	private static float MOVE_SPEED = 600f/GameScreen.PIX_PER_UNIT;
	
	private final Rectangle hitBox;
	
	public final float type; 
	
	public Obstacle(float x, float y, int type) {
		super(x, y);
		this.type = 5;
		if (type == 1) {
			hitBox = new Rectangle(x, y, LOG_WIDTH, LOG_HEIGHT);
		} else if (type == 2) {
			hitBox = new Rectangle(x, y, FISH_WIDTH, FISH_HEIGHT);
		} else if (type == 3) {
			hitBox = new Rectangle(x, y, ROCK_WIDTH, ROCK_HEIGHT);
		} else if (type == 4) {
			hitBox = new Rectangle(x, y, SHARK_WIDTH, SHARK_HEIGHT);
		} else {
			hitBox = new Rectangle(x, y, OCTOPUS_WIDTH, OCTOPUS_HEIGHT);
		}
	}
	
	public Rectangle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}

	@Override
	public void addTime(float delta) {
		position.y -= MOVE_SPEED * delta;
	}

	public int getDamage() {
		return OBSTACLE_DAMAGE;
	}

	
	
}
