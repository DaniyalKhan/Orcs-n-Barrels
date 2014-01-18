package com.gca.models.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LineProjectile extends Projectile {

	public final Vector2 end;
	
	public LineProjectile(float x, float y, float length, float velocity, float angle, int damage) {
		super(x, y, velocity, angle, damage);
		end = new Vector2(x + MathUtils.cos(angle) * length, y + MathUtils.sin(angle) * length);
	}

}
