package com.gca.models;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;


public class Orc extends Character implements Timeable {
	
	private static final int HEALTH = 5;
	private static final int ARROW_DAMAGE = 1;
	public static final float ORC_WIDTH = 75/GameScreen.PIX_PER_UNIT;
	public static final float ORC_HEIGHT = 75/GameScreen.PIX_PER_UNIT;
	public static final float MOVE_SPEED = 350f/GameScreen.PIX_PER_UNIT;
	
	private static final Random RAND = new Random();
	private final Rectangle hitBox;	
	private float timeSinceShoot;
	private final Vector2 destination;
	private float shootTimer;
	
	public float animTime;
	public float angle;
	public boolean shooting;
	
	public float redFilter = 1f;
	
	public float deadOpacity = 1f;
	
	public Orc(float x, float y) {
		super(x, y, HEALTH);
		this.hitBox = new Rectangle(x, y, ORC_WIDTH, ORC_HEIGHT);
		this.timeSinceShoot = 0;
		this.destination = new Vector2();
	}
	
	public void setDestination(float x, float y) {
		destination.x = x;
		destination.y = y;
	}
	
	public Rectangle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}
	
	public boolean shootArrow() {
		if (timeSinceShoot >= shootTimer && destination.y == position.y) {
			timeSinceShoot = 0f;
			shootTimer = (RAND.nextFloat() * 2) + 1;
			shooting = true;
			animTime = 0f;
			return true;
		}
		return false;
	}

	@Override
	public void addTime(float delta) {
		timeSinceShoot += delta;
		animTime += delta;
		float deltaY = destination.y - position.y;
		if (deltaY > 0) {
			position.y += delta * MOVE_SPEED;
			if (destination.y - position.y < 0) { 
				position.y = destination.y;
				timeSinceShoot = 0;
				shootTimer = 1f;
			}
		}
		if (redFilter < 1f) {
			redFilter += delta * 0.5f;
			if (redFilter > 1f) redFilter = 1f;
		} 
	}
	
	public static int getMaxHealth() {
		return HEALTH;
	}
	
	public int getDamage() {
		return ARROW_DAMAGE;
	}
	public static class EliteOrc extends Orc {

		public static final int ARROW_DAMAGE = 2;
		public static final int HEALTH = 8;
		
		public EliteOrc(float x, float y) {
			super(x, y);
		}
		
		public int getDamage() {
			return ARROW_DAMAGE;
		}
		
		public static int getMaxHealth() {
			return HEALTH;
		}
		
	}
	
}
