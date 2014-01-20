package com.gca.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.Life;
import com.gca.models.Obstacle;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.projectiles.LineProjectile;
import com.gca.models.projectiles.Spell;

public class CollisionDetector {
	
	
	private static final Vector2 v1 = new Vector2();

	private static Intersector intersector = new Intersector();
	
	private static Rectangle rectangle = new Rectangle();
	
	private static Circle circle = new Circle();
	
	public static final boolean wizardHit(Wizard w, LineProjectile p) {
		Circle whb = w.getHitBox();
		v1.x = whb.x;
		v1.y = whb.y;
		return Intersector.distanceLinePoint(p.ls.p1, p.ls.p2, v1) <= whb.radius*0.7f;
		
	}
	
	public static final boolean orcHit(Orc o, Spell s) {
		if (Intersector.overlaps(o.getHitBox(), s.getHitBox())) {
			SoundPlayer.orcHit();
			return true;
		}
		return false;
	}
	
	public static boolean lifeGet (Wizard w, Life l) {
		circle.x = l.position.x + Wizard.SIZE/2f;
		circle.y = l.position.y + Wizard.SIZE/2f;
		circle.radius = Wizard.RADIUS;
		return Intersector.overlaps(w.getHitBox(), circle);
	}
	
	
	public static boolean wizardHit (Wizard w, Obstacle o) {
		rectangle.set(o.getHitBox());
		rectangle.x -=0.3f;
		rectangle.width *= 0.8f;
		if (o.type == 3) rectangle.width *= 0.7f;
		return Intersector.overlaps(w.getHitBox(), rectangle);
	}
	
}
