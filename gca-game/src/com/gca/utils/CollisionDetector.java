package com.gca.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.Wizard;
import com.gca.models.projectiles.LineProjectile;

public class CollisionDetector {
	
	private static Intersector intersector = new Intersector();
	
	private static final Vector2 v1 = new Vector2();
	private static final Vector2 v2 = new Vector2();
	private static final Vector2 v3 = new Vector2();
	
	public static final boolean wizardHit(Wizard w, LineProjectile p) {
		Circle whb = w.getHitBox();
		
		v1.x = (p.end.x - p.position.x);
		v1.y = (p.end.y - p.position.y);
		v2.x = v1.x;
		v2.y = v1.y; //arrow
		v1.nor(); //normalized length of arrow
		
		v3.x = whb.x - p.position.x;
		v3.y = whb.y - p.position.y; //arrow to circle
		
		float proj = v1.dot(v3);
		
		Vector2 closest; //closest point to circle
		
		if (proj < 0) closest = p.position;
		else if (proj > v2.len()) closest = p.end;
		else {
			closest = p.position.add(v1.scl(proj));
		}

		return closest.len() <= whb.radius;
		
	}

}
