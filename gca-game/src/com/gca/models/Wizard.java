package com.gca.models;

import com.badlogic.gdx.math.Circle;
import com.gca.models.projectiles.Projectile;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;

public class Wizard extends Character implements Timeable {

	private static final int HEALTH = 1; //5
	public static final float SIZE = 100f/GameScreen.PIX_PER_UNIT;
	public static final float WIZARD_WIDTH = 88f/GameScreen.PIX_PER_UNIT;
	public static final float WIZARD_HEIGHT = 80f/GameScreen.PIX_PER_UNIT;
	
	private static final float RADIUS = (float) (Math.sqrt(2) * SIZE)/2f;
	private static final float DAMAGE_RESET = 2.5f; //2.5
	
	private final Circle hitBox;
	
	public boolean hurt;
	private float hurtTimer;
	
	public float responseThreshold;
	
	float bottomY;
	float topY;
	
	public float deathOpacity = 1f; 
	
	public final static int STILL = 0x000;
	public final static int LEFT = 0x001;
	public final static int RIGHT = 0x002;
	public int direction;
	
	public Wizard(float x, float y, float width, float height, float response, float bottomY, float topY) {
		super(x, y, HEALTH);
		hitBox = new Circle();
		hitBox.radius = RADIUS;
		hurt = false;
		hurtTimer = DAMAGE_RESET;
		responseThreshold = response;
		direction = STILL;
		this.bottomY = bottomY;
		this.topY = topY;
	}
	
	public Circle getHitBox() {
		hitBox.x = position.x;
		hitBox.y = position.y;
		return hitBox;
	}
	
	@Override
	public boolean onHit(Projectile projectile) {
		if (hurt) return false;
		startHurtTimer();
		return super.onHit(projectile);
	}

	public boolean onHit(Obstacle obstacle) {
		if (hurt) return false;
		startHurtTimer();
		return damage(obstacle.getDamage());
	}
	
	private void startHurtTimer() {
		hurt = true;
		hurtTimer = 0;
	}

	@Override
	public void addTime(float delta) {
		if (hurt) {
			hurtTimer += delta;
			if (hurtTimer >= DAMAGE_RESET) hurt = false;
		}
		
	}

}
