package com.gca.models;

import com.gca.models.projectiles.Projectile;


public class Character extends GameModel {

	protected int health;
	
	public Character(float x, float y, int health) {
		super(x, y);
		this.health = health;
	}
	
	public boolean onHit(Projectile projectile) {
		return damage(projectile.getDamage());
	}
	
	protected final boolean damage(int damage) {
		health -= damage;
		if (health <= 0) return true;
		return false;
	}
	
}
