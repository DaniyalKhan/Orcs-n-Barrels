package com.gca.models.projectiles;

import com.badlogic.gdx.math.Vector2;

public class LineProjectile extends Projectile {

	public final Vector2 end;
	
	public LineProjectile(float x, float y, Vector2 end, Vector2 velocity, float angle, int damage) {
		super(x, y, velocity, angle, damage);
		this.end = end;
	}

}
