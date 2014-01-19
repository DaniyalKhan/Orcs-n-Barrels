package com.gca.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.gca.models.Obstacle;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.projectiles.LineProjectile;
import com.gca.models.projectiles.Spell;

public class CollisionDetector {
	
	
	private static Intersector intersector = new Intersector();
	
	private static final Vector2 v1 = new Vector2();
	private static final Vector2 v2 = new Vector2();
	private static final Vector2 v3 = new Vector2();
	private static Rectangle rectangle = new Rectangle();
	
	public static final boolean wizardHit(Wizard w, LineProjectile p) {
		Circle whb = w.getHitBox();
		v1.x = whb.x;
		v1.y = whb.y;
		return Intersector.distanceLinePoint(p.ls.p1, p.ls.p2, v1) <= whb.radius*0.7f;
		
	}
	
	public static final boolean orcHit(Orc o, Spell s) {
		return Intersector.overlaps(o.getHitBox(), s.getHitBox());
	}
	
	public static boolean wizardHit (Wizard w, Obstacle o) {
		rectangle.set(o.getHitBox());
		rectangle.x -=0.2f;
		return Intersector.overlaps(w.getHitBox(), rectangle);
	}
	
}
