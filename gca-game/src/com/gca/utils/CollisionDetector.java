package com.gca.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.Obstacle;
import com.gca.models.Wizard;
import com.gca.models.projectiles.LineProjectile;

public class CollisionDetector {
	
	private static Intersector intersector = new Intersector();
	
	private static final Vector2 v1 = new Vector2();
	private static final Vector2 v2 = new Vector2();
	private static final Vector2 v3 = new Vector2();
	private static final Vector2 closest = new Vector2();
	
	public static final boolean wizardHit(Wizard w, LineProjectile p) {
		Circle whb = w.getHitBox();
		
		v1.x = p.length * MathUtils.cos(p.angle);
		v1.y = p.length * MathUtils.sin(p.angle);
		
//		v1.x = (p.end.x - p.position.x);
//		v1.y = (p.end.y - p.position.y);
		v2.x = v1.x;
		v2.y = v1.y; //arrow
		v1.nor(); //normalized length of arrow
		
		v3.x = whb.x - p.position.x;
		v3.y = whb.y - p.position.y; //arrow to circle
		
		float proj = v1.dot(v3);
		
		
		if (proj < 0) {
			closest.x = p.position.x;
			closest.y = p.position.y;
		}
		else if (proj > v2.len()) {
			closest.y = p.length * MathUtils.cos(p.angle) + p.position.x;
			closest.y = p.length * MathUtils.sin(p.angle) + p.position.y;
		}
		else {
			closest.x = p.position.x + v1.scl(proj).x;
			closest.y = p.position.y + v1.scl(proj).y;
		}

		return closest.len() <= whb.radius;
		
	}
	
	public static boolean wizardHit (Wizard w, Obstacle o) {
		return Intersector.overlaps(w.getHitBox(), o.getHitBox());
	}
 
	public static final boolean orcHit() {
		return false;
	}
	
}
