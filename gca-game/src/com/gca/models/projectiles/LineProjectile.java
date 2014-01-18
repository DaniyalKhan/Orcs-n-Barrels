package com.gca.models.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LineProjectile extends Projectile {
	
	public final float length;
	public final float angle;
	public final LineSegment ls;
	
	public LineProjectile(float x, float y, float length, Vector2 velocity, float angle, int damage) {
		super(x, y, velocity, damage);
		this.length = length;
		this.angle = angle;
		ls = new LineSegment(position.cpy(), new Vector2(length * MathUtils.cos(angle) + position.x, length * MathUtils.sin(angle) + position.y));
		if (velocity.x > 0 && velocity.y < 0 || velocity.x < 0 && velocity.y > 0) {
			float temp = ls.p1.x;
			ls.p1.x = ls.p2.x;
			ls.p2.x = temp;
		}
	}
	
	@Override
	public void addTime(float delta) {
		super.addTime(delta);
		ls.p1.x += delta * velocity.x;
		ls.p1.y += delta * velocity.y;
		ls.p2.x += delta * velocity.x;
		ls.p2.y += delta * velocity.y;
	}

	public class LineSegment {
		public Vector2 p1;
		public Vector2 p2;
		public LineSegment(Vector2 p1, Vector2 p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}

}
