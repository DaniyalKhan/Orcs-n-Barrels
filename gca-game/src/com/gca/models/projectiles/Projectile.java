package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gca.models.GameModel;
import com.gca.utils.Timeable;

public abstract class Projectile extends GameModel implements Timeable {

	protected final Vector2 velocity;
	private final int damage; 
	
	public final float angle;
	
	public Projectile(float x, float y, Vector2 velocity, float angle, int damage) {
		super(x, y);
		this.velocity = velocity;
		this.damage = damage;
		this.angle = angle;
	}

	@Override
	public void addTime(float delta) {
		move(velocity.x * delta, velocity.y * delta);
	}
	
	public final int getDamage() {
		return damage;
	}

}
