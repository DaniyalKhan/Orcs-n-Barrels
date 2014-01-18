package com.gca.models;

import com.badlogic.gdx.math.Circle;
import com.gca.models.projectiles.Projectile;
import com.gca.screens.GameScreen;
import com.gca.utils.Timeable;

public class Wizard extends Character implements Timeable {

	private static final int HEALTH = 5;
	public static final float SIZE = 100f/GameScreen.PIX_PER_UNIT;
	
	private static final float RADIUS = (float) (Math.sqrt(2) * SIZE)/2f;
	private static final float DAMAGE_RESET = 1f;
	
	private final Circle hitBox;
	
	private boolean hurt;
	private float hurtTimer;
	
	public Wizard(float x, float y, float width, float height) {
		super(x, y, HEALTH);
		hitBox = new Circle();
		hitBox.radius = RADIUS;
		hurt = false;
		hurtTimer = DAMAGE_RESET;
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
