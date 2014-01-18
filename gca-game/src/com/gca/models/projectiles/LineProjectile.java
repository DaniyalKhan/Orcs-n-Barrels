package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;

public class LineProjectile extends Projectile {
	
	public final float length;
	public final float angle;
	
	public LineProjectile(float x, float y, float length, Vector2 velocity, float angle, int damage) {
		super(x, y, velocity, damage);
		this.length = length;
		this.angle = angle;
	}

}
